package com.zinbo.portfolio.config;

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

        // Allow health check without API key (for UptimeRobot)
        if (req.getRequestURI().equals("/api/health")) {
            chain.doFilter(req, res);
            return;
        }

        // Rate limiting — check IP first
        String ip = req.getRemoteAddr();
        Bucket bucket = getBucket(ip);
        if (!bucket.tryConsume(1)) {
            res.setStatus(429);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.getWriter().write("{\"success\":false,\"error\":\"Too many requests — slow down\"}");
            return;
        }

        // API key check — must send X-API-Key header
        String apiKey = req.getHeader("X-API-Key");
        if (apiKey == null || !apiKey.equals(validApiKey)) {
            res.setStatus(403);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.getWriter().write("{\"success\":false,\"error\":\"Forbidden — invalid or missing API key\"}");
            return;
        }

        chain.doFilter(req, res);
    }
}
