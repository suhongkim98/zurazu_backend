package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

@Data
public class MemberProfileDTO {
    private String name;
    private String profileUrl;
    private int tradeCount;
    private int applyCount;
    private int purchaseCount;
}
