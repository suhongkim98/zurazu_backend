package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;

public interface AdminDAOInterface {
    void registerAdmin(RegisterAdminDTO registerAdminDTO);
    AdminDTO findAdmin(LoginAdminDTO loginAdminDTO);
}
