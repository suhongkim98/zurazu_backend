package com.zurazu.zurazu_backend.web.dto;

import com.zurazu.zurazu_backend.core.enumtype.GenderType;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterApplySellProductDTO {
    @NotNull(message = "카테고리 인덱스가 필요합니다.")
    private Integer categoryIdx;
    private String brandName;
    private Integer purchasePrice;
    private Integer desiredPrice;
    @NotEmpty(message = "옷 사이즈 입력이 필요합니다.")
    private String clothingSize;
    @NotNull(message = "옷 상태 입력이 필요합니다. 0~4")
    @Max(4)
    private Integer clothingStatus;
    private GenderType gender = GenderType.MALE;
    private String comments;
}
