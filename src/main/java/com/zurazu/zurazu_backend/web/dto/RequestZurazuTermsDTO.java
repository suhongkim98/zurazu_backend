package com.zurazu.zurazu_backend.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestZurazuTermsDTO {
    @NotEmpty
    private String type;
}
