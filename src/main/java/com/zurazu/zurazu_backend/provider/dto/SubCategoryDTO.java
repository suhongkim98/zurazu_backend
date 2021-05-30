package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

@Data
public class SubCategoryDTO {
    private int idx;
    private MainCategoryDTO mainCategory;
    private String korean;
    private String english;
    private int priority;
}
