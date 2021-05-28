package com.zurazu.zurazu_backend.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RequestPurchaseDTO {
    @NotEmpty
    private String customerName;
    @NotEmpty
    private String customerPhone;
    @Email
    private String customerEmail;
    @NotEmpty
    private String registerNumber; // 상품 등록 번호
    @NotNull
    private Integer purchasePrice; // 원래는 결제 검증하고 해야하나 결제기능 없는 베타이므로 이렇게

    private int memberIdx;
    private int registerProductIdx;
}
