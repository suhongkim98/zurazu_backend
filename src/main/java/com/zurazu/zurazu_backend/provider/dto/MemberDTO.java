package com.zurazu.zurazu_backend.provider.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private int idx;
    private String email;
    private String password;
    private String refreshToken;
    private String accessToken;
    private String salt;
    private String grade;
    private String profileImageUrl;
}
