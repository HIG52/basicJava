package com.example.basicsamplesite.domain.dto;

public record MainResponse(
        String message
) {
    public static MainResponse of(String message) {
        return new MainResponse(message);
    }
}
