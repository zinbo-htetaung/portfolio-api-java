package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.model.ContactRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// POST /api/contact — validated request body, logs in dev mode
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    // POST /api/contact
    // @Valid triggers Jakarta validation on ContactRequest fields
    @PostMapping
    public ResponseEntity<ApiResponse<String>> contact(@Valid @RequestBody ContactRequest req) {

        // Dev mode: log to console. Wire up Spring Mail for production.
        System.out.printf("%n📬 Contact form submission:%n  From: %s <%s>%n  Message: %s%n%n",
            req.name(), req.email(), req.message());

        return ResponseEntity.ok(ApiResponse.ok("Message received. Thank you, " + req.name() + "!"));
    }
}
