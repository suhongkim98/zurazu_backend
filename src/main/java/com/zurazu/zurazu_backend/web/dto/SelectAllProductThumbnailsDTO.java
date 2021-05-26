package com.zurazu.zurazu_backend.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SelectAllProductThumbnailsDTO {
    @NotNull
    private Integer offset;
    @NotNull
    private Integer limit;
    @NotNull
    private Integer mainCategoryIdx;
    private Integer subCategoryIdx;
    private Boolean notOnlySelectProgressing;
}
