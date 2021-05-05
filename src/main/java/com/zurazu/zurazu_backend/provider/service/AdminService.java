package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.service.AdminServiceInterface;
import com.zurazu.zurazu_backend.exception.errors.AdminLevelFailedException;
import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.provider.repository.AdminDAO;
import com.zurazu.zurazu_backend.web.dto.LoginAdminDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterAdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements AdminServiceInterface {
    private final AdminDAO adminDAO;
    @Override
    public void registerAdmin(RegisterAdminDTO registerAdminDTO) {
        adminDAO.registerAdmin(registerAdminDTO);
    }
    @Override
    public Optional<AdminDTO> loginAdmin(LoginAdminDTO loginAdminDTO) {
        AdminDTO admin = adminDAO.findAdmin(loginAdminDTO);
        if(admin != null) {
            if(admin.getGrade() <= 0) {
                //레벨 부여가 되지 않은 경우
                throw new AdminLevelFailedException();
            }
        }
        return Optional.ofNullable(admin);
    }

}
