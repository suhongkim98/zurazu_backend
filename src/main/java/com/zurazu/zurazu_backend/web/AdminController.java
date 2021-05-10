package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.exception.errors.AdminLevelFailedException;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.exception.errors.LoginFailedException;
import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.provider.service.AdminService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RefreshTokenDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

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

        if(admin.getGrade() <= 0) {
            //레벨 부여가 되지 않은 경우
            throw new AdminLevelFailedException();
        }

        HashMap<String, Object> map = new HashMap<>();
        
        List<Object> list;
        list = Arrays.asList(admin.getAccessToken(),"Not Null");
        map.put("accessToken", list);
        list = Arrays.asList(admin.getRefreshToken(),"Not Null");
        map.put("refreshToken", list);

        CommonResponse response = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("로그인 성공")
                .list(map)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<CommonResponse> refreshTokenAdmin(@Valid RefreshTokenDTO adminDTO) {
        // refresh 토큰을 검증하고 맞다면 access token 발급
        String accessToken = adminService.validAdminRefreshToken(adminDTO).orElseThrow(()-> new CustomJwtRuntimeException());

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
