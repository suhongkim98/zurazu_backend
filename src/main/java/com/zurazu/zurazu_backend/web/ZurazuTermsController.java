package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.provider.dto.ZurazuTermsDTO;
import com.zurazu.zurazu_backend.provider.service.ZurazuTermsService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.RequestZurazuTermsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class ZurazuTermsController {
    private final ZurazuTermsService zurazuTermsService;

    @GetMapping("/zurazuTerms")
    public ResponseEntity<CommonResponse> getZurazuTerms(@Valid RequestZurazuTermsDTO requestZurazuTermsDTO) {
        ZurazuTermsDTO termsDTO = zurazuTermsService.getZurazuTerms(requestZurazuTermsDTO).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("terms", termsDTO);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("약관 조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
