package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.model.LoginRequest;
import com.zinbo.portfolio.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final String adminPassword;

    public AuthController(
        JwtUtil jwtUtil,
        @Value("${app.admin.password}") String adminPassword
    ) {
        this.jwtUtil = jwtUtil;
        this.adminPassword = adminPassword;
    }

    // POST /api/auth/login
    // Body: { "password": "admin123" }
    // Returns: { "token": "eyJ..." } — use as Bearer token on protected routes
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest req) {
        if (!adminPassword.equals(req.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid credentials"));
        }

        String token = jwtUtil.generate("admin");
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
            "token",     token,
            "expiresIn", "8h"
        )));
    }
}
