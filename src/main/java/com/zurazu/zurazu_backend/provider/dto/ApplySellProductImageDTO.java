package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ApplySellProductImageDTO {
    private int idx;
    private int applySellProductIdx;
    private String url;
    private Date date;
    private String tag;
}
