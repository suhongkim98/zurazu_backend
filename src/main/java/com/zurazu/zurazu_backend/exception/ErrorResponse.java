package com.zurazu.zurazu_backend.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ErrorResponse {
    private String code;
    private String message;
    private HttpStatus status;
}
