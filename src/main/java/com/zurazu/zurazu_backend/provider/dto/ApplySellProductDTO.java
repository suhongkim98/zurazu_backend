package com.zurazu.zurazu_backend.provider.dto;

import com.zurazu.zurazu_backend.core.enumtype.ApplySellStatusType;
import com.zurazu.zurazu_backend.core.enumtype.GenderType;
import lombok.Data;

import java.sql.Date;

@Data
public class ApplySellProductDTO {
    private int idx;
    private int memberIdx;
    private int categoryIdx;
    private String brandName;
    private int purchasePrice;
    private int desiredPrice;
    private String clothingSize;
    private int clothingStatus;
    private GenderType gender;
    private String comments;
    private Date date;
    private ApplySellStatusType saleStatus;
    private String orderNumber;
    private Date lockerTime; // 보관함 시간
}
