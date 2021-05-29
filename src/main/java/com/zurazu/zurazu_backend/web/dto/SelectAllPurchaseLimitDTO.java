package com.zurazu.zurazu_backend.web.dto;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SelectAllPurchaseLimitDTO {
    @NotNull
    private Integer offset;
    @NotNull
    private Integer limit;

    private int memberIdx;
    private SaleStatusType type;
}
