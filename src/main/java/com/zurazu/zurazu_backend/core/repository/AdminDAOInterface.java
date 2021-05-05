package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;

public interface AdminDAOInterface {
    void registerAdmin(AdminDTO adminDTO);
    AdminDTO findAdmin(String id);
    AdminDTO validAdmin(AdminDTO adminDTO); // 아이디 비밀번호 검증
    void updateRefreshToken(String id, String token);
}
