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
        this.repo = repo;
        this.geoService = geoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getViews() {
        return ResponseEntity.ok(ApiResponse.ok(snapshot()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> recordView(
        @RequestBody(required = false) Map<String, String> body,
        HttpServletRequest request
    ) {
        increment("total");
        if (body != null && body.containsKey("section")) {
            String section = body.get("section");
            if (section != null && !section.isBlank()) increment(section);
        }
        // Fire geo lookup in background — doesn't block the response
        geoService.logAsync(extractIp(request));
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
        // Log all headers to help diagnose Railway proxy behaviour
        String xff    = request.getHeader("X-Forwarded-For");
        String xReal  = request.getHeader("X-Real-IP");
        String remote = request.getRemoteAddr();
        System.out.println("🔍 IP headers — XFF: " + xff + " | X-Real-IP: " + xReal + " | remoteAddr: " + remote);

        // Walk XFF chain left-to-right, return first public IP
        if (xff != null && !xff.isBlank()) {
            for (String part : xff.split(",")) {
                String ip = part.trim();
                if (!geoService.isPrivate(ip)) return ip;
            }
        }
        if (xReal != null && !xReal.isBlank() && !geoService.isPrivate(xReal)) return xReal;
        return remote;
    }
}
