package com.zurazu.zurazu_backend.provider.dto;

import com.zurazu.zurazu_backend.core.enumtype.GenderType;
import lombok.Data;

import java.sql.Date;

@Data
public class PersonalInfoDTO {
    private int idx;
    private String realName;
    private GenderType gender;
    private Date birth;
    private String phoneNumber;
    private boolean agreeTermsOfService;
    private boolean agreeCollectionPersonalInfo;
    private boolean agreePushNotification;
    private boolean agreeReceiveEmail;
    private boolean agreeReceiveSMS;
    private boolean agreeReceiveKAKAO;
    private boolean agreeUpperFourteen;
}
