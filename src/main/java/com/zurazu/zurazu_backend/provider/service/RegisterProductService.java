package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.enumtype.ApplySellStatusType;
import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.core.service.RegisterProductServiceInterface;
import com.zurazu.zurazu_backend.exception.errors.NotFoundColorChipException;
import com.zurazu.zurazu_backend.exception.errors.NotFoundProductException;
import com.zurazu.zurazu_backend.provider.dto.ColorChipDTO;
import com.zurazu.zurazu_backend.provider.dto.ProductThumbnailDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductImageDTO;
import com.zurazu.zurazu_backend.provider.repository.ApplySellProductDAO;
import com.zurazu.zurazu_backend.provider.repository.CategoryDAO;
import com.zurazu.zurazu_backend.provider.repository.RegisterProductDAO;
import com.zurazu.zurazu_backend.util.S3Uploader;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllProductThumbnailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegisterProductService implements RegisterProductServiceInterface {
    private final RegisterProductDAO registerProductDAO;
    private final ApplySellProductDAO applySellProductDAO;
    private final CategoryDAO categoryDAO;
    private final S3Uploader s3Uploader;
    @Override
    public void registerProduct(RequestRegisterProductDTO requestRegisterProductDTO, Map<String, MultipartFile> fileMap) {
        //컬러칩 파일 없으면 익셉션 발행
        MultipartFile colorChipFile = fileMap.get("colorChipImage");
        if(colorChipFile == null) {
            //컬러칩이 없다.
            throw new NotFoundColorChipException();
        } else {
            fileMap.remove("colorChipImage"); // 꺼냈으니 리스트에서는 삭제
        }
        
        if(applySellProductDAO.getOneProduct(requestRegisterProductDTO.getApplySellProductIdx()) == null) {
            //판매 신청된 아이템이 없다
            throw new NotFoundProductException();
        }

        //판매 등록
        registerProductDAO.registerProduct(requestRegisterProductDTO);
        //신청 판매상품 상태 REGISTERED 로 변경
        applySellProductDAO.updateProductSaleStatus(ApplySellStatusType.REGISTERED, requestRegisterProductDTO.getApplySellProductIdx());

        //컬러칩 삽입 후 경로 반환
        String colorUrl = s3Uploader.upload(colorChipFile, "colorChipImages");

        //구한 경로로 디비 삽입
        ColorChipDTO colorChip = new ColorChipDTO();
        colorChip.setUrl(colorUrl);
        colorChip.setColorText(requestRegisterProductDTO.getColorText());
        colorChip.setRegisterProductIdx(requestRegisterProductDTO.getIdx());
        registerProductDAO.registerColorChip(colorChip);

        //일반 파일들 삽입 후 경로 반환하고 디비 삽입
        List<RegisterProductImageDTO> list = new ArrayList<>();
        if(fileMap != null) {
            fileMap.entrySet()
                    .stream()
                    .forEach(file -> {
                        String imageUrl = s3Uploader.upload(file.getValue(), "registerProductImages");
                        RegisterProductImageDTO image = new RegisterProductImageDTO();
                        image.setRegisterProductIdx(requestRegisterProductDTO.getIdx());
                        image.setUrl(imageUrl);

                        list.add(image);
                    });
        }
        if(list.size() > 0) {
            registerProductDAO.insertProductImages(list);
        }
    }

    @Override
    public Optional<List<ProductThumbnailDTO>> selectAllRegisterProductThumbnails(SelectAllProductThumbnailsDTO selectAllProductThumbnailsDTO) {
        return Optional.ofNullable(registerProductDAO.selectAllProductThumbnails(selectAllProductThumbnailsDTO));
    }

    @Override
    public Optional<RegisterProductDTO> selectOneRegisterProduct(int idx) {
        return Optional.ofNullable(registerProductDAO.selectOneRegisterProduct(idx));
    }

    @Override
    public Optional<List<RegisterProductImageDTO>> getAllProductImages(int productIdx) {
        return Optional.ofNullable(registerProductDAO.getAllImages(productIdx));
    }

    @Override
    public void updateRegisterProductStatus(SaleStatusType type, int productIdx) {
        registerProductDAO.updateRegisterProductStatus(type, productIdx);
    }
}
