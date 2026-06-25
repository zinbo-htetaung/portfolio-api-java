package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// In-memory visitor counter — swap for a DB (Spring Data JPA) for persistence
@RestController
@RequestMapping("/api/views")
public class ViewController {

    // AtomicInteger is thread-safe for concurrent requests
    private final AtomicInteger total = new AtomicInteger(0);
    private final ConcurrentHashMap<String, AtomicInteger> sections = new ConcurrentHashMap<>();

    // GET /api/views
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getViews() {
        return ResponseEntity.ok(ApiResponse.ok(snapshot()));
    }

    // POST /api/views
    // Body (optional): { "section": "hero" }
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> recordView(
        @RequestBody(required = false) Map<String, String> body
    ) {
        total.incrementAndGet();

        if (body != null && body.containsKey("section")) {
            String section = body.get("section");
            if (section != null && !section.isBlank()) {
                sections.computeIfAbsent(section, k -> new AtomicInteger(0)).incrementAndGet();
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(snapshot()));
    }

    private Map<String, Object> snapshot() {
        var sectionMap = new java.util.LinkedHashMap<String, Integer>();
        sections.forEach((k, v) -> sectionMap.put(k, v.get()));
        return Map.of("total", total.get(), "sections", sectionMap);
    }
}
