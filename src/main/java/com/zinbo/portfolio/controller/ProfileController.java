package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final PortfolioService svc;

    public ProfileController(PortfolioService svc) { this.svc = svc; }

    @GetMapping
    public ResponseEntity<?> getProfile() {
        var payload = new java.util.LinkedHashMap<String, Object>();
        payload.put("profile", svc.getProfile());
        payload.put("hero",    svc.getHero());
        payload.put("about",   svc.getAbout());
        return ResponseEntity.ok(Map.of("success", true, "data", payload));
    }

    @GetMapping("/education")
    public ResponseEntity<ApiResponse<?>> getEducation() {
        return ResponseEntity.ok(ApiResponse.ok(svc.getEducation()));
    }

    @GetMapping("/experience")
    public ResponseEntity<ApiResponse<?>> getExperience() {
        return ResponseEntity.ok(ApiResponse.ok(svc.getExperiences()));
    }

    @GetMapping("/experience/{id}")
    public ResponseEntity<?> getExperienceById(@PathVariable Long id) {
        return svc.getExperiences().stream()
            .filter(e -> id.equals(((Number) e.get("id")).longValue()))
            .findFirst()
            .map(e -> ResponseEntity.ok(Map.of("success", true, "data", e)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Experience with id " + id + " not found"));
    }
}
