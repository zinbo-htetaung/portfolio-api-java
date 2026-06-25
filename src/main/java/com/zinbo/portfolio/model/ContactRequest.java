package com.zinbo.portfolio.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Request body for POST /api/contact — validated with @Valid in the controller
public record ContactRequest(

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be under 100 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    String email,

    @NotBlank(message = "Message is required")
    @Size(min = 10, message = "Message must be at least 10 characters")
    @Size(max = 2000, message = "Message must be under 2000 characters")
    String message
) {}
