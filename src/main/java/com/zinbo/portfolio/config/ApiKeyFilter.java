package com.zinbo.portfolio.config;

import com.zinbo.portfolio.security.JwtUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api-key}")
    private String validApiKey;

    private final JwtUtil jwtUtil;

    public ApiKeyFilter(JwtUtil jwtUtil) { this.jwtUtil = jwtUtil; }

    // One rate-limit bucket per IP — 30 requests per minute
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket getBucket(String ip) {
        return buckets.computeIfAbsent(ip, k -> Bucket.builder()
            .addLimit(Bandwidth.classic(30, Refill.greedy(30, Duration.ofMinutes(1))))
            .build());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(req, res);
            return;
        }

        if (req.getRequestURI().equals("/api/health")) {
            chain.doFilter(req, res);
            return;
        }

        // Rate limiting — same for all routes
        String ip = req.getRemoteAddr();
        if (!getBucket(ip).tryConsume(1)) {
            reject(res, 429, "Too many requests — slow down");
            return;
        }

        String uri = req.getRequestURI();

        // Admin routes: require valid Bearer JWT (no API key needed)
        if (uri.startsWith("/api/admin")) {
            String auth = req.getHeader("Authorization");
            if (auth == null || !auth.startsWith("Bearer ")) {
                reject(res, 401, "Unauthorized — missing token");
                return;
            }
            if (!jwtUtil.isValid(auth.substring(7))) {
                reject(res, 401, "Unauthorized — invalid or expired token");
                return;
            }
            chain.doFilter(req, res);
            return;
        }

        // Auth login route: no API key required (admin app hits this directly)
        if (uri.startsWith("/api/auth")) {
            chain.doFilter(req, res);
            return;
        }

        // All other public routes: require X-API-Key
        String apiKey = req.getHeader("X-API-Key");
        if (apiKey == null || !apiKey.equals(validApiKey)) {
            reject(res, 403, "Forbidden — invalid or missing API key");
            return;
        }

        chain.doFilter(req, res);
    }

    private void reject(HttpServletResponse res, int status, String msg) throws IOException {
        res.setStatus(status);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write("{\"success\":false,\"error\":\"" + msg + "\"}");
    }
}
