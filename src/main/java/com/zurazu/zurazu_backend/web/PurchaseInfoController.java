package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.provider.dto.PurchaseProductDTO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.provider.service.PurchaseInfoService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllPurchaseLimitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PurchaseInfoController {
    private final PurchaseInfoService purchaseInfoService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @PostMapping("/member/product/purchase")
    ResponseEntity<CommonResponse> purchaseProduct(@Valid RequestPurchaseDTO requestPurchaseDTO, HttpServletRequest request) {
        String token = jwtAuthTokenProvider.resolveToken(request).orElseThrow(()->new CustomJwtRuntimeException());
        JwtAuthToken authToken = jwtAuthTokenProvider.convertAuthToken(token);
        int memberIdx = Integer.parseInt((String)authToken.getData().get("id"));
        requestPurchaseDTO.setMemberIdx(memberIdx);

        purchaseInfoService.purchaseProduct(requestPurchaseDTO);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("상품 구매 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/member/purchase/history")
    ResponseEntity<CommonResponse> memberPurchaseHistory(HttpServletRequest request, @Valid SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO){
        String token = jwtAuthTokenProvider.resolveToken(request).orElseThrow(()->new CustomJwtRuntimeException());
        JwtAuthToken authToken = jwtAuthTokenProvider.convertAuthToken(token);
        int memberIdx = Integer.parseInt((String)authToken.getData().get("id"));
        selectAllPurchaseLimitDTO.setMemberIdx(memberIdx);

        List<PurchaseProductDTO> list = purchaseInfoService.selectAllMemberPurchaseHistory(selectAllPurchaseLimitDTO).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("history", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("구매 내역 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/member/purchase/confirm") // 구매확정
    ResponseEntity<CommonResponse> memberPurchaseConfirm(HttpServletRequest request, String orderNumber) {
        String token = jwtAuthTokenProvider.resolveToken(request).orElseThrow(()->new CustomJwtRuntimeException());
        JwtAuthToken authToken = jwtAuthTokenProvider.convertAuthToken(token);
        int memberIdx = Integer.parseInt((String)authToken.getData().get("id"));
        purchaseInfoService.confirmPurchase(memberIdx, orderNumber);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("구매 확정 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/purchase/history")
    ResponseEntity<CommonResponse> purchaseHistory(@Valid SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO){
        List<PurchaseProductDTO> list = purchaseInfoService.selectAllPurchaseHistory(selectAllPurchaseLimitDTO).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("history", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("구매 내역 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/purchase/history/{type}")
    ResponseEntity<CommonResponse> purchaseHistory(@PathVariable SaleStatusType type, @Valid SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO){
        selectAllPurchaseLimitDTO.setType(type);
        List<PurchaseProductDTO> list = purchaseInfoService.selectAllPurchaseHistoryByType(selectAllPurchaseLimitDTO).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("history", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("구매 내역 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
