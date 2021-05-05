package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;

import java.util.Optional;

public interface AdminServiceInterface {
    void registerAdmin(RegisterAdminDTO registerAdminDTO);
    Optional<AdminDTO> loginAdmin(LoginAdminDTO loginAdminDTO);
}
