package com.zurazu.zurazu_backend.web.dto;

import com.zurazu.zurazu_backend.core.GenderType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class RegisterMemberDTO {
    @NotEmpty(message = "이메일이 비어있습니다.")
    @Size(min = 1, max = 50)
    @Email(message = "이메일 형태여야 합니다.")
    private String email;
    @NotEmpty(message = "비밀번호가 비었습니다.")
    private String password;

    @Size(min = 1, max = 30)
    private String realName;
    private GenderType gender;
    private Date birth;
    private String phoneNumber;
    private Boolean agreeTermsOfService = false;
    private Boolean agreeCollectionPersonalInfo = false;
    private Boolean agreePushNotification = false;
    private Boolean agreeReceiveEmail = false;
    private Boolean agreeReceiveSMS = false;
    private Boolean agreeReceiveKAKAO = false;
    private Boolean agreeUnderFourteen = false;
}
