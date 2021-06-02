package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.provider.dto.MemberProfileDTO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.provider.service.MemberService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final MemberService memberService;

    @GetMapping("member/profile")
    public ResponseEntity<CommonResponse> getProfileInfo(HttpServletRequest request) {
        String token = jwtAuthTokenProvider.resolveToken(request).orElseThrow(()->new CustomJwtRuntimeException());
        JwtAuthToken authToken = jwtAuthTokenProvider.convertAuthToken(token);
        int memberIdx = Integer.parseInt((String)authToken.getData().get("id"));

        MemberProfileDTO memberProfile = memberService.getProfileInfo(memberIdx).orElseGet(()->null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("profile", memberProfile);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("조회 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
