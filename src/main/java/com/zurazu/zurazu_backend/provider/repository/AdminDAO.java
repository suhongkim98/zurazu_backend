package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.AdminDAOInterface;
import com.zurazu.zurazu_backend.core.security.Role.Role;
import com.zurazu.zurazu_backend.exception.errors.LoginFailedException;
import com.zurazu.zurazu_backend.exception.errors.RegisterFailException;
import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.util.SHA256Util;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class AdminDAO implements AdminDAOInterface {
    private final SqlSessionTemplate sqlSession;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public void registerAdmin(RegisterAdminDTO registerAdminDTO) {
        AdminDTO admin = sqlSession.selectOne("adminTable.searchAdmin", registerAdminDTO.getId());

        if(admin != null) { // 이미 아이디가 존재하는 경우
            throw new RegisterFailException(); // 등록 실패
        }

        Date expiredDate = Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant()); // refresh토큰은 유효기간이 1년
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(registerAdminDTO.getId(), Role.ADMIN.getCode(), expiredDate);  //토큰 발급

        String salt = SHA256Util.generateSalt(); // salt
        String encryptedPassword = SHA256Util.getEncrypt(registerAdminDTO.getPassword(), salt); // 암호화

        admin = new AdminDTO();
        admin.setId(registerAdminDTO.getId());
        admin.setPassword(encryptedPassword);
        admin.setSalt(salt);
        admin.setRefreshToken(refreshToken.getToken());
        sqlSession.insert("adminTable.insertAdmin", admin);
    }

    @Override
    public AdminDTO findAdmin(LoginAdminDTO loginAdminDTO) {
        AdminDTO tmp = sqlSession.selectOne("adminTable.searchAdmin", loginAdminDTO.getId());
        if(tmp == null) {
            // 아이디 없음
            return null; // 컨트롤러에서 exception 처리
        }
        String salt = tmp.getSalt();
        String encryptedPassword = SHA256Util.getEncrypt(loginAdminDTO.getPassword(), salt);

        AdminDTO admin = new AdminDTO();
        admin.setId(loginAdminDTO.getId());
        admin.setPassword(encryptedPassword);
        return sqlSession.selectOne("adminTable.validAdmin", admin);
    }
}
