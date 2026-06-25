package com.zinbo.portfolio.config;

import com.zinbo.portfolio.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

// Catches all exceptions across every controller — no try/catch needed in routes
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation errors from @Valid — collect all field messages
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                f -> f.getField(),
                f -> f.getDefaultMessage(),
                (a, b) -> a
            ));

        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "error",   "Validation failed",
            "details", errors
        ));
    }

    // 404 / 401 etc. thrown with ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<String>> handleStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
            .body(ApiResponse.error(ex.getReason()));
    }

    // Catch-all for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("Internal server error: " + ex.getMessage()));
    }
}
