package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.ColorChipDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductImageDTO;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;

import java.util.List;

public interface RegisterProductDAOInterface {
    void registerProduct(RequestRegisterProductDTO requestRegisterProductDTO);
    List<RegisterProductDTO> selectAllRegisterProducts(int offset, int limit);
    RegisterProductDTO selectOneRegisterProduct(int idx);
    void registerColorChip(ColorChipDTO colorChipDTO);
    void insertProductImages(List<RegisterProductImageDTO> list);
}
