package com.zurazu.zurazu_backend.provider.dto;

import lombok.Data;

@Data
public class ProductThumbnailDTO {
    private int productIdx;
    private String name;
    private String brand;
    private RegisterProductImageDTO image;
}
