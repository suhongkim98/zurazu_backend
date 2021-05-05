package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.core.security.Role.Role;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.exception.errors.LoginFailedException;
import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.provider.service.AdminService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RefreshTokenAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerAdmin(@Valid RegisterAdminDTO adminDTO) { //@Valid로 파라미터 validation
        adminService.registerAdmin(adminDTO); // 실패 시 service나 repo가 exception 던짐

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("DB에 등록 성공")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> loginAdmin(@Valid LoginAdminDTO adminDTO) {
        AdminDTO admin = adminService.loginAdmin(adminDTO).orElseThrow(()->new LoginFailedException());

        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()); // 토큰은 2분만 유지되도록 설정, 2분 후 refresh token
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(admin.getId(), Role.ADMIN.getCode(), expiredDate);  //토큰 발급
        String refreshToken = admin.getRefreshToken();

        HashMap<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken.getToken());
        map.put("refreshToken", refreshToken);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("로그인 성공")
                .data(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<CommonResponse> refreshTokenAdmin(@Valid RefreshTokenAdminDTO adminDTO) {
        // refresh 토큰을 검증하고 맞다면 access token 발급
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(adminDTO.getRefreshToken());
        if(!jwtAuthToken.validate()) {
            throw new CustomJwtRuntimeException();
        }
        String id = String.valueOf(jwtAuthToken.getData().get("id"));

        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()); // 토큰은 2분만 유지되도록 설정, 2분 후 refresh token
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(id, Role.ADMIN.getCode(), expiredDate);  //토큰 발급

        HashMap<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken.getToken());

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("검증 성공")
                .data(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
