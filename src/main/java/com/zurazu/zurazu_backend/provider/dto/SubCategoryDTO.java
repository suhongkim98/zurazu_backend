package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

@Data
public class SubCategoryDTO {
    private int idx;
    private int mainCategoryIdx;
    private String name;
    private int priority;
}
