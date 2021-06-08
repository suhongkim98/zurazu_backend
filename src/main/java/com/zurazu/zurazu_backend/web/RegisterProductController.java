package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.provider.dto.ProductThumbnailDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductImageDTO;
import com.zurazu.zurazu_backend.provider.service.RegisterProductService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllProductThumbnailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegisterProductController {
    private final RegisterProductService registerProductService;

    @PostMapping("/admin/product/register")
    ResponseEntity<CommonResponse> registerProduct(@Valid RequestRegisterProductDTO registerProductDTO, MultipartHttpServletRequest multipartHttpServletRequest) {
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        registerProductService.registerProduct(registerProductDTO, fileMap);

        HashMap<String, Object> map = new HashMap<>();

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("등록 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/product")
    ResponseEntity<CommonResponse> selectAllProduct(@Valid SelectAllProductThumbnailsDTO selectAllProductThumbnailsDTO) {
        List<ProductThumbnailDTO> list = registerProductService.selectAllRegisterProductThumbnails
                (selectAllProductThumbnailsDTO).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("products", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/product/{productIdx}")
    ResponseEntity<CommonResponse> selectOneProduct(@PathVariable int productIdx) {
        RegisterProductDTO product = registerProductService.selectOneRegisterProduct(productIdx).orElseGet(()->null);
        List<RegisterProductImageDTO> images = registerProductService.getAllProductImages(productIdx).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("images", images);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/product/status/update")
    ResponseEntity<CommonResponse> updateProductStatus(@RequestBody Map<String, Object> params){
        SaleStatusType type = (SaleStatusType)params.get("type");
        int productIdx = (int)params.get("productIdx");
        registerProductService.updateRegisterProductStatus(type, productIdx);

        HashMap<String, Object> map = new HashMap<>();

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
