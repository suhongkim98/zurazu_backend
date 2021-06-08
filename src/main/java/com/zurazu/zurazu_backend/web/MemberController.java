package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.exception.errors.LoginFailedException;
import com.zurazu.zurazu_backend.provider.dto.MemberDTO;
import com.zurazu.zurazu_backend.provider.service.MemberService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.MemberWebDTO;
import com.zurazu.zurazu_backend.web.dto.RefreshTokenDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerMember(@RequestBody @Valid RegisterMemberDTO memberWebDTO) {
        memberService.registerMemberByEmail(memberWebDTO);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("등록 성공")
                .list(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> loginMember(@RequestBody @Valid MemberWebDTO memberWebDTO) {
        MemberDTO member = memberService.loginMemberByEmail(memberWebDTO).orElseThrow(()->new LoginFailedException());

        HashMap<String, Object> map = new HashMap<>();
        List<Object> list;
        list = Arrays.asList(member.getAccessToken(),"Not Null");
        map.put("accessToken", list);
        list = Arrays.asList(member.getRefreshToken(),"Not Null");
        map.put("refreshToken", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("로그인 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<CommonResponse> refreshTokenMember(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
        // refresh 토큰을 검증하고 맞다면 access token 발급
        String accessToken = memberService.validMemberRefreshToken(refreshTokenDTO).orElseThrow(()-> new CustomJwtRuntimeException());

        HashMap<String, Object> map = new HashMap<>();
        List<Object> list;
        list = Arrays.asList(accessToken,"Not Null");
        map.put("accessToken", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("검증 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
