package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.service.RegisterProductService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllLimitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/admin/product")
    ResponseEntity<CommonResponse> selectAllProduct(@Valid SelectAllLimitDTO selectAllLimitDTO) {
        List<RegisterProductDTO> list = registerProductService.selectAllRegisterProducts
                (selectAllLimitDTO.getOffset(), selectAllLimitDTO.getLimit())
                .orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("products", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/admin/product/{productIdx}")
    ResponseEntity<CommonResponse> selectOneProduct(@PathVariable int productIdx) {
        RegisterProductDTO product = registerProductService.selectOneRegisterProduct(productIdx).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("product", product);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
