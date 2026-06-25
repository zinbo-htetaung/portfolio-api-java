package com.zinbo.portfolio.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Password is required")
    String password
) {}
