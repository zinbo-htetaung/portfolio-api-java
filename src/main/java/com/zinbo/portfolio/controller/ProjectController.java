package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.data.PortfolioData;
import com.zinbo.portfolio.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final PortfolioData data;

    public ProjectController(PortfolioData data) {
        this.data = data;
    }

    // GET /api/projects          — all projects
    // GET /api/projects?tag=Java — filter by tag (case-insensitive)
    @GetMapping
    public ResponseEntity<?> getProjects(@RequestParam(required = false) String tag) {
        var projects = data.getProjects();

        if (tag != null && !tag.isBlank()) {
            projects = projects.stream()
                .filter(p -> ((List<String>) p.get("tags")).stream()
                    .anyMatch(t -> t.equalsIgnoreCase(tag)))
                .toList();
        }

        return ResponseEntity.ok(Map.of(
            "success", true,
            "count",   projects.size(),
            "data",    projects
        ));
    }

    // GET /api/projects/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable int id) {
        return data.getProjects().stream()
            .filter(p -> ((int) p.get("id")) == id)
            .findFirst()
            .map(p -> ResponseEntity.ok(Map.of("success", true, "data", p)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Project with id " + id + " not found"));
    }
}
