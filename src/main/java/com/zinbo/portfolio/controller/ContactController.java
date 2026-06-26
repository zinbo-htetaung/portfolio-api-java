package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.model.ContactRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Value("${app.hcaptcha.secret}")
    private String hcaptchaSecret;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> contact(@Valid @RequestBody ContactRequest req) {
        if (!verifyCaptcha(req.captchaToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Captcha verification failed");
        }

        System.out.printf("%n📬 Contact form submission:%n  From: %s <%s>%n  Message: %s%n%n",
            req.name(), req.email(), req.message());

        return ResponseEntity.ok(ApiResponse.ok("Message received. Thank you, " + req.name() + "!"));
    }

    private boolean verifyCaptcha(String token) {
        try {
            String body = "response=" + URLEncoder.encode(token, StandardCharsets.UTF_8)
                        + "&secret="   + URLEncoder.encode(hcaptchaSecret, StandardCharsets.UTF_8);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hcaptcha.com/siteverify"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("🔐 hCaptcha response: " + response.body());
            return response.body().contains("\"success\":true");
        } catch (Exception e) {
            System.out.println("🔐 hCaptcha exception: " + e.getMessage());
            return false;
        }
    }
}
