package com.zurazu.zurazu_backend.provider.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {
    private int idx;
    private String id;
    private String password;
    private String salt;
    private int grade; // 어드민 등급
    private String refreshToken;
}
