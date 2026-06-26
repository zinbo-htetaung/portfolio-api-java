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

    @Value("${app.resend.api-key:}")
    private String resendApiKey;

    @Value("${app.mail.to:}")
    private String mailTo;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> contact(@Valid @RequestBody ContactRequest req) {
        if (!verifyCaptcha(req.captchaToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Captcha verification failed");
        }

        sendEmail(req);

        System.out.printf("%n📬 Contact form submission:%n  From: %s <%s>%n  Message: %s%n%n",
            req.name(), req.email(), req.message());

        return ResponseEntity.ok(ApiResponse.ok("Message received. Thank you, " + req.name() + "!"));
    }

    private void sendEmail(ContactRequest req) {
        if (resendApiKey == null || resendApiKey.isBlank()) return;
        if (mailTo == null || mailTo.isBlank()) return;

        new Thread(() -> {
            try {
                String text = "Name:    " + req.name()  + "\n"
                            + "Email:   " + req.email() + "\n\n"
                            + req.message();

                // Escape for JSON
                String body = "{"
                    + "\"from\":\"Portfolio <onboarding@resend.dev>\","
                    + "\"to\":[\"" + jsonEscape(mailTo) + "\"],"
                    + "\"reply_to\":\"" + jsonEscape(req.email()) + "\","
                    + "\"subject\":\"Portfolio Contact: " + jsonEscape(req.name()) + "\","
                    + "\"text\":\"" + jsonEscape(text) + "\""
                    + "}";

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + resendApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200 || response.statusCode() == 201) {
                    System.out.println("📧 Email sent via Resend to " + mailTo);
                } else {
                    System.out.println("⚠️ Resend error " + response.statusCode() + ": " + response.body());
                }
            } catch (Exception e) {
                System.out.println("⚠️ Email send failed: " + e.getMessage());
            }
        }).start();
    }

    private static String jsonEscape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private boolean verifyCaptcha(String token) {
        try {
            String secret = hcaptchaSecret.trim();
            String body = "response=" + URLEncoder.encode(token.trim(), StandardCharsets.UTF_8)
                        + "&secret="   + URLEncoder.encode(secret, StandardCharsets.UTF_8);

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
