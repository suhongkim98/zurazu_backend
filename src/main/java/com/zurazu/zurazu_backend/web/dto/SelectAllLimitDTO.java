package com.zurazu.zurazu_backend.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SelectAllLimitDTO {
    @NotNull
    private Integer offset;
    @NotNull
    private Integer limit;
}
