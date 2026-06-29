package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.entity.PageViewEntity;
import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.repository.PageViewRepository;
import com.zinbo.portfolio.service.GeoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/views")
public class ViewController {

    private final PageViewRepository repo;
    private final GeoService geoService;

    public ViewController(PageViewRepository repo, GeoService geoService) {
        this.repo       = repo;
        this.geoService = geoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getViews() {
        return ResponseEntity.ok(ApiResponse.ok(snapshot()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> recordView(
        @RequestBody(required = false) Map<String, Object> body,
        HttpServletRequest request
    ) {
        if (body != null && body.containsKey("section")) {
            String section = (String) body.get("section");
            increment("total");
            if (section != null && !section.isBlank()) increment(section);
        } else {
            increment("total");
        }

        String ip = extractIp(request);
        String ua = request.getHeader("User-Agent");

        var visitorData = new GeoService.VisitorData(
            parseBrowser(ua),
            parseOs(ua),
            parseDevice(ua),
            body != null ? (String) body.getOrDefault("referrer", request.getHeader("Referer") != null ? request.getHeader("Referer") : "direct") : "direct",
            body != null ? (String) body.getOrDefault("language", request.getHeader("Accept-Language")) : request.getHeader("Accept-Language"),
            body != null ? (String) body.getOrDefault("screen", "") : "",
            body != null ? (String) body.getOrDefault("timezone", "") : "",
            body != null && body.get("darkMode") instanceof Boolean b ? b : null
        );

        geoService.logAsync(ip, visitorData);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(snapshot()));
    }

    public Map<String, Object> snapshot() {
        long total = 0;
        var sections = new LinkedHashMap<String, Long>();
        for (PageViewEntity e : repo.findAll()) {
            if ("total".equals(e.getSection())) total = e.getCount();
            else sections.put(e.getSection(), e.getCount());
        }
        return Map.of("total", total, "sections", sections);
    }

    private synchronized void increment(String section) {
        var e = repo.findById(section).orElseGet(() -> {
            var n = new PageViewEntity();
            n.setSection(section);
            n.setCount(0);
            return n;
        });
        e.increment();
        repo.save(e);
    }

    private String extractIp(HttpServletRequest request) {
        String cf = request.getHeader("CF-Connecting-IP");
        if (cf != null && !cf.isBlank() && !geoService.isPrivate(cf)) {
            System.out.println("🔍 IP from CF-Connecting-IP: " + cf);
            return cf.trim();
        }
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            for (String part : xff.split(",")) {
                String ip = part.trim();
                if (!geoService.isPrivate(ip)) {
                    System.out.println("🔍 IP from X-Forwarded-For: " + ip);
                    return ip;
                }
            }
        }
        String xReal = request.getHeader("X-Real-IP");
        if (xReal != null && !xReal.isBlank() && !geoService.isPrivate(xReal)) {
            System.out.println("🔍 IP from X-Real-IP: " + xReal);
            return xReal;
        }
        return request.getRemoteAddr();
    }

    // ── User-Agent parsing ─────────────────────────────────────────

    private String parseBrowser(String ua) {
        if (ua == null) return "Unknown";
        if (ua.contains("Edg/"))                          return "Edge";
        if (ua.contains("OPR/") || ua.contains("Opera")) return "Opera";
        if (ua.contains("SamsungBrowser"))                return "Samsung Browser";
        if (ua.contains("Chrome/"))                       return "Chrome";
        if (ua.contains("Firefox/"))                      return "Firefox";
        if (ua.contains("Safari/") && ua.contains("Version/")) return "Safari";
        return "Other";
    }

    private String parseOs(String ua) {
        if (ua == null) return "Unknown";
        if (ua.contains("Windows NT 10.0") || ua.contains("Windows NT 11.0")) return "Windows 10/11";
        if (ua.contains("Windows"))         return "Windows";
        if (ua.contains("iPhone"))          return "iOS (iPhone)";
        if (ua.contains("iPad"))            return "iOS (iPad)";
        if (ua.contains("Android"))         return "Android";
        if (ua.contains("Mac OS X"))        return "macOS";
        if (ua.contains("Linux"))           return "Linux";
        return "Other";
    }

    private String parseDevice(String ua) {
        if (ua == null) return "Unknown";
        if (ua.contains("iPhone") || ua.contains("Android") && ua.contains("Mobile")) return "Mobile";
        if (ua.contains("iPad") || ua.contains("Tablet")) return "Tablet";
        return "Desktop";
    }
}
