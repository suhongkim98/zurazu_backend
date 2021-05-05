package com.zurazu.zurazu_backend.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterAdminDTO {
    @NotEmpty(message = "아이디는 비어있으면 안 된다.")
    @Size(min = 1, max = 30, message = "1 ~ 30자 까지만 가능하다.")
    String id;

    @Size(min = 1, max = 30, message = "1 ~ 30자 까지만 가능하다.")
    @NotEmpty(message = "비밀번호는 비어있으면 안 된다.")
    String password;
}
