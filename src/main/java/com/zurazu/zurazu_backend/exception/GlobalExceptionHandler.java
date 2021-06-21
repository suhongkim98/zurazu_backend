package com.zurazu.zurazu_backend.exception;

import com.zurazu.zurazu_backend.exception.errors.*;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        // 없는 경로로 요청 시
        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.NOT_FOUND_PATH.getStatus())
                .message(ErrorCode.NOT_FOUND_PATH.getMessage())
                .code(ErrorCode.NOT_FOUND_PATH.getCode())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        // GET POST 방식이 잘못된 경우
        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .message(ErrorCode.METHOD_NOT_ALLOWED.getMessage())
                .code(ErrorCode.METHOD_NOT_ALLOWED.getCode())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode())
                .message(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getMessage())
                .status(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getStatus())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(FileSizeLimitExceededException.class)
    protected ResponseEntity<ErrorResponse> handleFileSizeLimitExceededException(FileSizeLimitExceededException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.FILE_SIZE_ESCEED.getCode())
                .message(ErrorCode.FILE_SIZE_ESCEED.getMessage())
                .status(ErrorCode.FILE_SIZE_ESCEED.getStatus())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(SizeLimitExceededException.class)
    protected ResponseEntity<ErrorResponse> handleSizeLimitExceededException(SizeLimitExceededException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.REQUEST_FILE_SIZE_EXCEED.getCode())
                .message(ErrorCode.REQUEST_FILE_SIZE_EXCEED.getMessage())
                .status(ErrorCode.REQUEST_FILE_SIZE_EXCEED.getStatus())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Bean Validation에 실패했을 때, 에러메시지를 내보내기 위한 Exception Handler
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleParamViolationException(BindException ex) {
        // 파라미터 validation에 걸렸을 경우
        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.REQUEST_PARAMETER_BIND_FAILED.getStatus())
                .message(ex.getMessage())
                .code(ErrorCode.REQUEST_PARAMETER_BIND_FAILED.getCode())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(CustomJwtRuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleJwtException(CustomJwtRuntimeException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.INVALID_JWT_TOKEN.getCode())
                .message(ErrorCode.INVALID_JWT_TOKEN.getMessage())
                .status(ErrorCode.INVALID_JWT_TOKEN.getStatus())
                .build();

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(RegisterFailException.class)
    protected ResponseEntity<ErrorResponse> handleRegisterFailException(RegisterFailException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.REGISTER_FAILED.getCode())
                .message(ErrorCode.REGISTER_FAILED.getMessage())
                .status(ErrorCode.REGISTER_FAILED.getStatus())
                .build();

        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(LoginFailedException.class)
    protected ResponseEntity<ErrorResponse> handleLoginFailedException(LoginFailedException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.LOGIN_FAILED.getCode())
                .message(ErrorCode.LOGIN_FAILED.getMessage())
                .status(ErrorCode.LOGIN_FAILED.getStatus())
                .build();

        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(AdminLevelFailedException.class)
    protected ResponseEntity<ErrorResponse> handleAdminLevelFailedException(AdminLevelFailedException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.ADMIN_LEVEL_FAILED.getCode())
                .message(ErrorCode.ADMIN_LEVEL_FAILED.getMessage())
                .status(ErrorCode.ADMIN_LEVEL_FAILED.getStatus())
                .build();

        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(NotFoundCategoryException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundCategoryException(NotFoundCategoryException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.NOT_FOUND_CATEGORY.getCode())
                .message(ErrorCode.NOT_FOUND_CATEGORY.getMessage())
                .status(ErrorCode.NOT_FOUND_CATEGORY.getStatus())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(NotFoundTypeException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundTypeException(NotFoundTypeException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.NOT_FOUND_TYPE.getCode())
                .message(ErrorCode.NOT_FOUND_TYPE.getMessage())
                .status(ErrorCode.NOT_FOUND_TYPE.getStatus())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(NotFoundColorChipException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundColorChipException(NotFoundColorChipException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.NOT_FOUND_COLOR_CHIP.getCode())
                .message(ErrorCode.NOT_FOUND_COLOR_CHIP.getMessage())
                .status(ErrorCode.NOT_FOUND_COLOR_CHIP.getStatus())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(NotFoundProductException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundProductException(NotFoundProductException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.NOT_FOUND_PRODUCT.getCode())
                .message(ErrorCode.NOT_FOUND_PRODUCT.getMessage())
                .status(ErrorCode.NOT_FOUND_PRODUCT.getStatus())
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

}
