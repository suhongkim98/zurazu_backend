package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.security.Role.Role;
import com.zurazu.zurazu_backend.core.service.AdminServiceInterface;
import com.zurazu.zurazu_backend.exception.errors.AdminLevelFailedException;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.exception.errors.RegisterFailException;
import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.provider.repository.AdminDAO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.util.SHA256Util;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RefreshTokenAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements AdminServiceInterface {
    private final AdminDAO adminDAO;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public void registerAdmin(RegisterAdminDTO registerAdminDTO) {
        String refreshToken = createRefreshToken(registerAdminDTO.getId());
        String salt = SHA256Util.generateSalt(); // salt
        String encryptedPassword = SHA256Util.getEncrypt(registerAdminDTO.getPassword(), salt); // 암호화

        AdminDTO admin = new AdminDTO();
        admin.setId(registerAdminDTO.getId());
        admin.setPassword(encryptedPassword);
        admin.setSalt(salt);
        admin.setRefreshToken(refreshToken);

        adminDAO.registerAdmin(admin);
    }
    @Override
    public Optional<AdminDTO> loginAdmin(LoginAdminDTO loginAdminDTO) {
        AdminDTO admin = adminDAO.findAdmin(loginAdminDTO.getId()); // 아이디가 없다.
        if(admin == null) {
            return Optional.empty();
        }

        //솔트 꺼내서 평문을 암호화한 후
        //비교
        String salt = admin.getSalt();
        String encryptedPassword = SHA256Util.getEncrypt(loginAdminDTO.getPassword(), salt);

        admin.setPassword((encryptedPassword));
        admin = adminDAO.validAdmin(admin); // 비밀번호가 틀렸다.
        if(admin == null) {
            return Optional.empty();
        }

        String accessToken = createAccessToken(admin.getId());

        // db에서 꺼낸 refresh토큰이 만료된 경우 재발급
        JwtAuthToken jwtAuthRefreshToken = jwtAuthTokenProvider.convertAuthToken(admin.getRefreshToken());
        String refreshToken = jwtAuthRefreshToken.getToken();
        if(!jwtAuthRefreshToken.validate()) {
            refreshToken = createRefreshToken(admin.getId());
            // 재발급 받았으니 디비에 삽입
            adminDAO.updateRefreshToken(admin.getId(), refreshToken);
        }

        admin.setAccessToken(accessToken);
        admin.setRefreshToken(refreshToken);

        return Optional.ofNullable(admin);
    }

    @Override
    public Optional<String> validAdminRefreshToken(RefreshTokenAdminDTO refreshTokenAdminDTO) {
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(refreshTokenAdminDTO.getRefreshToken());
        if(!jwtAuthToken.validate()) {
            return Optional.empty();
        }

        String id = String.valueOf(jwtAuthToken.getData().get("id"));

        String accessToken = createAccessToken(id);
        return Optional.ofNullable(accessToken);
    }

    @Override
    public String createAccessToken(String id) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()); // 토큰은 2분만 유지되도록 설정, 2분 후 refresh token
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(id, Role.ADMIN.getCode(), expiredDate);  //토큰 발급

        return accessToken.getToken();
    }

    @Override
    public String createRefreshToken(String id) {
        Date expiredDate = Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant()); // refresh토큰은 유효기간이 1년
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(id, Role.ADMIN.getCode(), expiredDate);  //토큰 발급
        return refreshToken.getToken();
    }


}
