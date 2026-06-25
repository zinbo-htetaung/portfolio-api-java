package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.data.PortfolioData;
import com.zinbo.portfolio.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final PortfolioData data;

    public SkillController(PortfolioData data) {
        this.data = data;
    }

    // GET /api/skills
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSkills() {
        return ResponseEntity.ok(ApiResponse.ok(data.getSkills()));
    }

    // GET /api/skills/technical
    @GetMapping("/technical")
    public ResponseEntity<ApiResponse<?>> getTechnical() {
        return ResponseEntity.ok(ApiResponse.ok(data.getSkills().get("technical")));
    }

    // GET /api/skills/languages
    @GetMapping("/languages")
    public ResponseEntity<ApiResponse<?>> getLanguages() {
        return ResponseEntity.ok(ApiResponse.ok(data.getSkills().get("languages")));
    }
}
