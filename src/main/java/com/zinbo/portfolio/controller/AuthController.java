package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.model.LoginRequest;
import com.zinbo.portfolio.security.JwtUtil;
import com.zinbo.portfolio.service.OtpService;
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
    private final OtpService otpService;
    private final String adminPassword;

    public AuthController(
        JwtUtil jwtUtil,
        OtpService otpService,
        @Value("${app.admin.password}") String adminPassword
    ) {
        this.jwtUtil = jwtUtil;
        this.otpService = otpService;
        this.adminPassword = adminPassword;
    }

    // Step 1: verify password → send OTP to email
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest req) {
        if (!adminPassword.equals(req.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid credentials"));
        }
        otpService.generateAndSend();
        return ResponseEntity.ok(ApiResponse.ok(Map.of("otpSent", true)));
    }

    // Step 2: verify OTP → return JWT
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verify(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        if (code == null || !otpService.verify(code)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Invalid or expired code"));
        }
        String token = jwtUtil.generate("admin");
        return ResponseEntity.ok(ApiResponse.ok(Map.of("token", token, "expiresIn", "8h")));
    }
}
