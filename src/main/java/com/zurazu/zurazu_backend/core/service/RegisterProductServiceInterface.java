package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RegisterProductServiceInterface {
    void registerProduct(RequestRegisterProductDTO requestRegisterProductDTO, Map<String, MultipartFile> fileMap);
    Optional<List<RegisterProductDTO>> selectAllRegisterProducts(int offset, int limit);
    Optional<RegisterProductDTO> selectOneRegisterProduct(int idx);
}
