package com.example.Nov.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {

    private String message;
    private int status;
    private String path;
    private LocalDateTime timestamp;

    public ErrorResponseDto(String message, LocalDateTime timestamp, String path, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
