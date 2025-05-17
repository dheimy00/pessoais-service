package com.pessoais.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private List<String> details;

    public static ErrorResponse of(String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .errorCode(errorCode)
                .message(message)
                .path(path)
                .build();
    }

    public static ErrorResponse of(String errorCode, String message, String path, List<String> details) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .errorCode(errorCode)
                .message(message)
                .path(path)
                .details(details)
                .build();
    }
}
