package com.zurazu.zurazu_backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
	NOT_FOUND_PATH(HttpStatus.NOT_FOUND, "PATH_001", "NOT FOUND PATH"), // 없는 경로로 요청보낸 경우
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,"PATH_002","METHOD NOT ALLOWED"), // POST GET방식 잘못 보낸경우
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "PATH_003", "UNSUPPORTED MEDIA TYPE"),
    REQUEST_FILE_SIZE_EXCEED(HttpStatus.BAD_REQUEST, "FILE001", "총 파일 크기가 제한을 넘었습니다."),
    FILE_SIZE_ESCEED(HttpStatus.BAD_REQUEST, "FILE002", "특정 파일 크기가 제한을 넘었습니다"),

	
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_001", " AUTHENTICATION_FAILED."),
    LOGIN_FAILED(HttpStatus.NOT_FOUND, "AUTH_002", " LOGIN_FAILED."),
    REGISTER_FAILED(HttpStatus.FORBIDDEN, "AUTH_003", "REGISTER_FAILED"),
    REQUEST_PARAMETER_BIND_FAILED(HttpStatus.BAD_REQUEST, "REQ_001", "PARAMETER_BIND_FAILED"),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH004", "INVALID_JWT_TOKEN."),
    ADMIN_LEVEL_FAILED(HttpStatus.UNAUTHORIZED, "AUTH005", "ADMIN LEVEL IS TOO LOW"),
	NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "CATEGORY001", "NOT FOUND CATEGORY"),
    NOT_FOUND_TYPE(HttpStatus.NOT_FOUND,"TYPE001", "NOT FOUND TYPE"),
    NOT_FOUND_COLOR_CHIP(HttpStatus.NOT_FOUND,"PRODUCT001", "NOT FOUND COLOR CHIP"),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND,"PRODUCT002", "NOT FOUND PRODUCT"),
    FORBIDDEN_DELETE_PRODUCT(HttpStatus.FORBIDDEN,"PURCHASE001", "구매내역이 존재합니다.");
    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
