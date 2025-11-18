package com.example.multipartTest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse {
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
