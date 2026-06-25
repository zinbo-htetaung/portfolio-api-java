package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.data.PortfolioData;
import com.zinbo.portfolio.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final PortfolioData data;

    public ProfileController(PortfolioData data) {
        this.data = data;
    }

    // GET /api/profile
    @GetMapping
    public ResponseEntity<?> getProfile() {
        var payload = new java.util.LinkedHashMap<String, Object>();
        payload.put("profile", data.getProfile());
        payload.put("hero",    data.getHero());
        payload.put("about",   data.getAbout());
        return ResponseEntity.ok(Map.of("success", true, "data", payload));
    }

    // GET /api/profile/education
    @GetMapping("/education")
    public ResponseEntity<ApiResponse<?>> getEducation() {
        return ResponseEntity.ok(ApiResponse.ok(data.getEducation()));
    }

    // GET /api/profile/experience
    @GetMapping("/experience")
    public ResponseEntity<ApiResponse<?>> getExperience() {
        return ResponseEntity.ok(ApiResponse.ok(data.getExperiences()));
    }

    // GET /api/profile/experience/{id}
    @GetMapping("/experience/{id}")
    public ResponseEntity<?> getExperienceById(@PathVariable int id) {
        return data.getExperiences().stream()
            .filter(e -> ((int) e.get("id")) == id)
            .findFirst()
            .map(e -> ResponseEntity.ok(Map.of("success", true, "data", e)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Experience with id " + id + " not found"));
    }
}
