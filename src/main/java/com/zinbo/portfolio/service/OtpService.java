package com.zinbo.portfolio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    @Value("${app.resend.api-key:}")
    private String resendApiKey;

    @Value("${app.mail.to:}")
    private String mailTo;

    private record OtpEntry(String code, Instant expiresAt) {}

    private final ConcurrentHashMap<String, OtpEntry> store = new ConcurrentHashMap<>();
    private final SecureRandom rng = new SecureRandom();

    private static final String KEY = "admin";
    private static final int TTL_SECONDS = 300; // 5 minutes

    public void generateAndSend() {
        String code = String.format("%06d", rng.nextInt(1_000_000));
        store.put(KEY, new OtpEntry(code, Instant.now().plusSeconds(TTL_SECONDS)));
        sendEmail(code);
    }

    public boolean verify(String code) {
        OtpEntry entry = store.get(KEY);
        if (entry == null) return false;
        if (Instant.now().isAfter(entry.expiresAt())) { store.remove(KEY); return false; }
        if (!entry.code().equals(code)) return false;
        store.remove(KEY);
        return true;
    }

    private void sendEmail(String code) {
        if (resendApiKey == null || resendApiKey.isBlank()) {
            System.out.println("⚠️  No Resend key — OTP code: " + code);
            return;
        }
        new Thread(() -> {
            try {
                String body = "{"
                    + "\"from\":\"Portfolio Admin <onboarding@resend.dev>\","
                    + "\"to\":[\"" + mailTo + "\"],"
                    + "\"subject\":\"Your admin login code\","
                    + "\"text\":\"Your verification code is: " + code + "\\n\\nExpires in 5 minutes.\""
                    + "}";

                HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + resendApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

                var res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
                System.out.println("📧 OTP email sent: " + res.statusCode());
            } catch (Exception e) {
                System.out.println("⚠️  OTP email failed: " + e.getMessage());
            }
        }).start();
    }
}
