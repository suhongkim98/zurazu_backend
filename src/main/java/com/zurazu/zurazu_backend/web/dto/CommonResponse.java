package com.zurazu.zurazu_backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder //.build로 객체 생성하게 해줌
public class CommonResponse {
    // restcontroller 응답에 사용되는 dto객체
    private HttpStatus status;
    private String message;
    private Object data;
}