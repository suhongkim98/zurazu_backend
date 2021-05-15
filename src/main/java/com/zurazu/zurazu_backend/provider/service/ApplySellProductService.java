package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.service.ApplySellProductServiceInterface;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductDTO;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductImageDTO;
import com.zurazu.zurazu_backend.provider.repository.ApplySellProductDAO;
import com.zurazu.zurazu_backend.util.S3Uploader;
import com.zurazu.zurazu_backend.web.dto.RegisterApplySellProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplySellProductService implements ApplySellProductServiceInterface {
    private final ApplySellProductDAO applySellProductDAO;
    private final S3Uploader s3Uploader;

    @Override
    public void registerProduct(RegisterApplySellProductDTO registerApplySellProductDTO, Map<String, MultipartFile> fileMap, int idx) {
        //판매 신청 등록
        ApplySellProductDTO sellProductDTO = new ApplySellProductDTO();
        sellProductDTO.setMemberIdx(idx);
        sellProductDTO.setPrice(registerApplySellProductDTO.getPrice());
        sellProductDTO.setBrandName(registerApplySellProductDTO.getBrandName());
        sellProductDTO.setCategoryIdx(registerApplySellProductDTO.getCategoryIdx());
        sellProductDTO.setClothingSize(registerApplySellProductDTO.getClothingSize());
        sellProductDTO.setComments(registerApplySellProductDTO.getComments());
        sellProductDTO.setClothingStatus(registerApplySellProductDTO.getClothingStatus());
        sellProductDTO.setGender(registerApplySellProductDTO.getGender());

        applySellProductDAO.registerProduct(sellProductDTO);
        //판매신청 이미지 등록
        List<ApplySellProductImageDTO> list = new ArrayList<>();
        if(fileMap != null) {
            fileMap.entrySet()
                    .stream()
                    .forEach(file -> {
                        ApplySellProductImageDTO applySellProductImageDTO = new ApplySellProductImageDTO();
                        applySellProductImageDTO.setApplySellProductIdx(sellProductDTO.getIdx());
                        //s3에 파일 업로드
                        String s3UploadUrl = s3Uploader.upload(file.getValue(), "applySellProductImages");
                        applySellProductImageDTO.setUrl(s3UploadUrl);

                        list.add(applySellProductImageDTO);
                    });
        }
        applySellProductDAO.registerProductImages(list);
    }

    @Override
    public Optional<List<ApplySellProductDTO>> getAllProducts() {
        //이미 인터셉터에서 토큰검증이 되었다.
        return Optional.ofNullable(applySellProductDAO.getAllProducts());
    }

    @Override
    public Optional<List<ApplySellProductDTO>> getAllMyProducts(int idx) {
        return Optional.ofNullable(applySellProductDAO.getAllMyProducts(idx));
    }

    @Override
    public Optional<ApplySellProductDTO> getOneProduct(int idx) {
        return Optional.ofNullable(applySellProductDAO.getOneProduct(idx));
    }
}
