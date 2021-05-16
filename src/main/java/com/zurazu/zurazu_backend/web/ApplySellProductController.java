package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.core.security.Role.Role;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductDTO;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductImageDTO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.provider.service.ApplySellProductService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.RegisterApplySellProductDTO;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApplySellProductController {
    private final ApplySellProductService applySellProductService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @PostMapping("/member/applySellProduct/register") // 멤버 토큰이 필요한 작업
    public ResponseEntity<CommonResponse> registerSellProduct(@Valid RegisterApplySellProductDTO registerApplySellProductDTO, HttpServletRequest request, MultipartHttpServletRequest multipartHttpServletRequest) {
        //토큰에서 idx 꺼내
        String token = jwtAuthTokenProvider.resolveToken(request).orElseThrow(()->new CustomJwtRuntimeException());
        JwtAuthToken authToken = jwtAuthTokenProvider.convertAuthToken(token);

        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap(); // 다중파일 가져오기

        applySellProductService.registerProduct(registerApplySellProductDTO, fileMap, Integer.parseInt((String) authToken.getData().get("id")));

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("상품 등록 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/member/applySellProduct") // 멤버 토큰이 필요한 작업
    public ResponseEntity<CommonResponse> getAllMySellProduct(HttpServletRequest request, @Valid SelectAllLimitDTO selectAllLimitDTO){
        // 토큰에서 idx
        String token = jwtAuthTokenProvider.resolveToken(request).orElseThrow(()->new CustomJwtRuntimeException());
        JwtAuthToken authToken = jwtAuthTokenProvider.convertAuthToken(token);

        List<ApplySellProductDTO> products = applySellProductService.getAllMyProducts(
                Integer.parseInt((String)authToken.getData().get("id")),
                selectAllLimitDTO.getOffset(),
                selectAllLimitDTO.getLimit()
        ).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("products", products);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("상품 조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/member/applySellProduct/{productIdx}") // 멤버 토큰이 필요한 작업
    public ResponseEntity<CommonResponse> getOneMySellProduct(@PathVariable int productIdx, HttpServletRequest request){
        // 토큰에서 idx
        String token = jwtAuthTokenProvider.resolveToken(request).orElseThrow(()->new CustomJwtRuntimeException());
        JwtAuthToken authToken = jwtAuthTokenProvider.convertAuthToken(token);
        String role = (String)authToken.getData().get("role");
        int memberIdx = Integer.parseInt((String)authToken.getData().get("id"));

        ApplySellProductDTO applySellProductDTO = applySellProductService.getOneProduct(productIdx).orElseGet(()->null);
        if(applySellProductDTO != null) {
            if(role.equals(Role.USER.getCode()) && memberIdx != applySellProductDTO.getMemberIdx()) {
                // 오우,!! 관리자나 멤버 본인 만이 해당 정보를 볼 수 있답니다.
                throw new CustomJwtRuntimeException();
            }
        }

        List<ApplySellProductImageDTO> images = applySellProductService.getAllProductImages(productIdx).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("product", applySellProductDTO);
        map.put("images", images);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("상품 조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/applySellProduct") // 어드민 토큰이 필요한 작업
    public ResponseEntity<CommonResponse> getAllSellProducts(@Valid SelectAllLimitDTO selectAllLimitDTO) {
        List<ApplySellProductDTO> products = applySellProductService.getAllProducts(
                selectAllLimitDTO.getOffset(),
                selectAllLimitDTO.getLimit()
        ).orElseGet(() -> null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("products", products);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("상품 조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
