package com.zurazu.zurazu_backend.provider.dto;

import com.zurazu.zurazu_backend.core.GenderType;
import lombok.Data;

import java.sql.Date;

@Data
public class ApplySellProductDTO {
    private int idx;
    private int memberIdx;
    private int categoryIdx;
    private String brandName;
    private int price;
    private String clothingSize;
    private int clothingStatus;
    private GenderType gender;
    private String comments;
    private Date date;
}
