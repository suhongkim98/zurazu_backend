package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.provider.dto.ProductThumbnailDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductImageDTO;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllProductThumbnailsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RegisterProductServiceInterface {
    void registerProduct(RequestRegisterProductDTO requestRegisterProductDTO, Map<String, MultipartFile> fileMap);
    Optional<List<ProductThumbnailDTO>> selectAllRegisterProductThumbnails(SelectAllProductThumbnailsDTO selectAllProductThumbnailsDTO);
    Optional<RegisterProductDTO> selectOneRegisterProduct(int idx);
    Optional<List<RegisterProductImageDTO>> getAllProductImages(int productIdx);
    void updateRegisterProductStatus(SaleStatusType type, int productIdx);
}
