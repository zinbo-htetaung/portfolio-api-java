package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final PortfolioService svc;

    public ProjectController(PortfolioService svc) { this.svc = svc; }

    @GetMapping
    public ResponseEntity<?> getProjects(@RequestParam(required = false) String tag) {
        var projects = svc.getProjects();
        if (tag != null && !tag.isBlank()) {
            projects = projects.stream()
                .filter(p -> ((List<String>) p.get("tags")).stream()
                    .anyMatch(t -> t.equalsIgnoreCase(tag)))
                .toList();
        }
        return ResponseEntity.ok(Map.of("success", true, "count", projects.size(), "data", projects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        return svc.getProjects().stream()
            .filter(p -> id.equals(((Number) p.get("id")).longValue()))
            .findFirst()
            .map(p -> ResponseEntity.ok(Map.of("success", true, "data", p)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Project with id " + id + " not found"));
    }
}
