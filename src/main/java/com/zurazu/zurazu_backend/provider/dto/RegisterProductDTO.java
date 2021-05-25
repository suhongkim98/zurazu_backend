package com.zurazu.zurazu_backend.provider.dto;

import com.zurazu.zurazu_backend.core.enumtype.ClothingStatusType;
import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import lombok.Data;

import java.sql.Date;

@Data
public class RegisterProductDTO {
    private int idx;
    private SubCategoryDTO subCategory;
    private ColorChipDTO colorChip;
    private int applySellProductIdx;    //판매신청 인덱스
    private String name;    //상품 이름
    private String brand;   //브랜드
    private int price;
    private String material;    //소재
    private ClothingStatusType clothingStatusType;      // 옷상태
    private int inspectionStatus; //검수기준
    private String clothingSize;    // 사이즈
    private String infoComment;
    private String laundryComment;
    private String searchKeyword;
    private SaleStatusType saleStatusType;  //판매 중인지
    private String registerNumber;
    private Date createDate;
}