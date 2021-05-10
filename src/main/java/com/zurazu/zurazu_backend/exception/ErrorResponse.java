package com.zurazu.zurazu_backend.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

import org.springframework.http.HttpStatus;
import java.util.UUID;

@Builder
@Getter
public class ErrorResponse {
	@Builder.Default
	private String id = UUID.randomUUID().toString(); // uuid
	@Builder.Default
	private Date dateTime = new Date();
    private String code;
    private String message;
    private HttpStatus status;
}
