package com.zurazu.zurazu_backend.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginAdminDTO {
    @NotEmpty(message = "아이디가 비어있음.")
    private String id;
    @NotEmpty(message = "비밀번호가 비어있음.")
    private String password;
}
