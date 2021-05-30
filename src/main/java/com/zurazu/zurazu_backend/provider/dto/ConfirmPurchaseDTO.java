package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

@Data
public class ConfirmPurchaseDTO {
    private String orderNumber;
    private boolean isConfirm;
}
