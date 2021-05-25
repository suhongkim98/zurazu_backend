package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class RegisterProductImageDTO {
    private int idx;
    private int registerProductIdx;
    private String url;
    private Date createDate;
}
