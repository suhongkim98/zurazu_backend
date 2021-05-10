package com.zurazu.zurazu_backend.web.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.UUID;


@Getter
@Builder //.build로 객체 생성하게 해줌
public class CommonResponse {
    // restcontroller 응답에 사용되는 dto객체
	@Builder.Default // Builder default 지정
	private String id = UUID.randomUUID().toString(); // uuid
	@Builder.Default 
	private Date dateTime = new Date(); // date
    private HttpStatus status;
    private String message;
    private Object list;
}