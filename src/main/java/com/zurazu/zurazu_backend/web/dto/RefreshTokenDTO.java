package com.zurazu.zurazu_backend.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RefreshTokenDTO {
    @NotEmpty(message = "토큰이 담기지 않음")
    private String refreshToken;
}
