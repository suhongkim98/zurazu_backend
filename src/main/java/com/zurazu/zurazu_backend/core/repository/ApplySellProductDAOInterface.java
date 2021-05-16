package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.ApplySellProductDTO;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductImageDTO;

import java.util.List;

public interface ApplySellProductDAOInterface {
    void registerProduct(ApplySellProductDTO applySellProductDTO);
    void registerProductImages(List<ApplySellProductImageDTO> list);
    List<ApplySellProductDTO> getAllProducts();
    List<ApplySellProductDTO> getAllMyProducts(int idx);
    ApplySellProductDTO getOneProduct(int idx);
    List<ApplySellProductImageDTO> getAllProductImages(int productIdx);
}
