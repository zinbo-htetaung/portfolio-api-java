package com.zinbo.portfolio.model;

// Generic wrapper for every API response — keeps the shape consistent
public record ApiResponse<T>(boolean success, T data) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data);
    }

    public static ApiResponse<String> error(String message) {
        return new ApiResponse<>(false, message);
    }
}
