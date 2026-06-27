package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final PortfolioService svc;

    public SkillController(PortfolioService svc) { this.svc = svc; }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSkills() {
        return ResponseEntity.ok(ApiResponse.ok(svc.getSkills()));
    }

    @GetMapping("/languages")
    public ResponseEntity<ApiResponse<?>> getLanguages() {
        return ResponseEntity.ok(ApiResponse.ok(svc.getSkills().get("languages")));
    }
}
