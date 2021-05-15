package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.provider.dto.ApplySellProductDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterApplySellProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApplySellProductServiceInterface {
    void registerProduct(RegisterApplySellProductDTO registerApplySellProductDTO, Map<String, MultipartFile> fileMap, int idx);
    Optional<List<ApplySellProductDTO>> getAllProducts();
    Optional<List<ApplySellProductDTO>> getAllMyProducts(int idx);
    Optional<ApplySellProductDTO> getOneProduct(int idx);
}
