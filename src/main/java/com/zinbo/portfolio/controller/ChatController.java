package com.zinbo.portfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinbo.portfolio.model.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${app.gemini.key:}")
    private String geminiKey;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient http    = HttpClient.newHttpClient();

    record Message(String role, String content) {}
    record ChatRequest(String systemPrompt, List<Message> history, String message) {}

    @PostMapping
    public ResponseEntity<ApiResponse<String>> chat(@RequestBody ChatRequest req) {
        if (geminiKey == null || geminiKey.isBlank()) {
            return ResponseEntity.ok(ApiResponse.ok("Chatbot is not configured yet."));
        }

        try {
            var contents = new ArrayList<Map<String, Object>>();

            // System prompt injected as the opening turn
            contents.add(Map.of("role", "user",
                "parts", List.of(Map.of("text", req.systemPrompt()))));
            contents.add(Map.of("role", "model",
                "parts", List.of(Map.of("text",
                    "Understood! I will only answer questions about Zin Bo based on the information provided."))));

            // Conversation history
            for (Message m : req.history()) {
                contents.add(Map.of(
                    "role",  m.role().equals("assistant") ? "model" : "user",
                    "parts", List.of(Map.of("text", m.content()))
                ));
            }

            // Current user message
            contents.add(Map.of("role", "user",
                "parts", List.of(Map.of("text", req.message()))));

            String body = mapper.writeValueAsString(Map.of(
                "contents",         contents,
                "generationConfig", Map.of("maxOutputTokens", 600, "temperature", 0.4)
            ));

            HttpRequest httpReq = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

            HttpResponse<String> resp = http.send(httpReq, HttpResponse.BodyHandlers.ofString());
            String reply = extractReply(resp.body());
            System.out.println("🤖 Gemini replied (" + reply.length() + " chars)");
            return ResponseEntity.ok(ApiResponse.ok(reply));

        } catch (Exception e) {
            System.out.println("⚠️ Gemini error: " + e.getMessage());
            return ResponseEntity.ok(ApiResponse.ok("Oops! Something went wrong. Please try again."));
        }
    }

    @SuppressWarnings("unchecked")
    private String extractReply(String responseBody) {
        try {
            Map<?, ?> root      = mapper.readValue(responseBody, Map.class);
            var candidates      = (List<?>) root.get("candidates");
            var content         = (Map<?, ?>) ((Map<?, ?>) candidates.get(0)).get("content");
            var parts           = (List<?>) content.get("parts");
            return ((String) ((Map<?, ?>) parts.get(0)).get("text")).trim();
        } catch (Exception e) {
            System.out.println("⚠️ Could not parse Gemini response: " + responseBody);
            return "Sorry, I couldn't get a response. Please try again!";
        }
    }
}
