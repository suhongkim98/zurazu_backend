package com.zurazu.zurazu_backend.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberWebDTO {
    @Email
    @Size(min = 1, max = 50)
    private String email;
    @NotEmpty(message = "비밀번호가 비었습니다.")
    private String password;
}
