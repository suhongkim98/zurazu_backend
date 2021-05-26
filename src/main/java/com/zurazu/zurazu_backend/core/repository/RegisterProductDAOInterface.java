package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.ColorChipDTO;
import com.zurazu.zurazu_backend.provider.dto.ProductThumbnailDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductImageDTO;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllProductThumbnailsDTO;

import java.util.List;

public interface RegisterProductDAOInterface {
    void registerProduct(RequestRegisterProductDTO requestRegisterProductDTO);
    List<ProductThumbnailDTO> selectAllProductThumbnails(SelectAllProductThumbnailsDTO selectAllProductThumbnailsDTO);
    RegisterProductDTO selectOneRegisterProduct(int idx);
    List<RegisterProductImageDTO> getAllImages(int productIdx);
    void registerColorChip(ColorChipDTO colorChipDTO);
    void insertProductImages(List<RegisterProductImageDTO> list);
}
