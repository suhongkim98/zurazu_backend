package com.zurazu.zurazu_backend.web.dto;

import com.zurazu.zurazu_backend.core.enumtype.ClothingStatusType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RequestRegisterProductDTO {
    private int idx;
    @NotNull(message = "판매 신청된 상품 인덱스가 필요")
    private Integer applySellProductIdx;
    @NotNull(message = "서브 카테고리 인덱스 적어야한다")
    private Integer subCategoryIdx;
    @NotEmpty(message = "제품이름 입력하자")
    private String name; // 제품 이름
    @NotNull(message = "가격 적어야함")
    private Integer price;
    private String brand;
    @NotNull(message = "컬러 입력")
    private String colorText; // 컬러칩은 멀티파트파일
    @NotEmpty(message = "소재 입력")
    private String material;
    @NotNull(message = "옷 등급 입력, A or B or C")
    private ClothingStatusType clothingStatus;
    @NotNull(message = "복종별 검수 기준, 0~4")
    private Integer inspectionStatus;
    @NotEmpty(message = "사이즈 입력")
    private String clothingSize;
    @NotEmpty(message = "세탁 및 취급 방법")
    private String laundryComment;
    @NotEmpty(message = "제품 상세 정보")
    private String infoComment;
    private String searchKeyword;

}
