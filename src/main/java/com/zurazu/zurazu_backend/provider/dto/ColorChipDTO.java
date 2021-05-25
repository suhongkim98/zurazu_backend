package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ColorChipDTO {
    private int idx;
    private int registerProductIdx;
    private String colorText;
    private String url;
    private Date createDate;
}
