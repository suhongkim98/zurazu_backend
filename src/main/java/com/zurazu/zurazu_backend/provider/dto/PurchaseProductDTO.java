package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class PurchaseProductDTO {
    private int idx;
    private RegisterProductDTO registerProduct;
    private MemberDTO member;
    private String orderNumber;
    private boolean confirmPurchase; // 구매확정 여부
    private Date createDate;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private int purchasePrice;
}
