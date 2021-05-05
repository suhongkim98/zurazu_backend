package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.AdminDAOInterface;
import com.zurazu.zurazu_backend.exception.errors.RegisterFailException;
import com.zurazu.zurazu_backend.provider.dto.AdminDTO;
import com.zurazu.zurazu_backend.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminDAO implements AdminDAOInterface {
    private final SqlSessionTemplate sqlSession;

    @Override
    public void registerAdmin(AdminDTO adminDTO) {
        AdminDTO isExistAdmin = findAdmin(adminDTO.getId());
        if(isExistAdmin != null) {
            // 이미 아이디가 존재
            throw new RegisterFailException();
        }
        sqlSession.insert("adminTable.insertAdmin", adminDTO);
    }

    @Override
    public AdminDTO findAdmin(String id) {
        //아이디로 검색
        AdminDTO admin = sqlSession.selectOne("adminTable.searchAdmin", id);
        return admin;
    }

    @Override
    public AdminDTO validAdmin(AdminDTO adminDTO) {
        return sqlSession.selectOne("adminTable.validAdmin", adminDTO);
    }

    @Override
    public void updateRefreshToken(String id, String token) {
        AdminDTO admin = new AdminDTO();
        admin.setId(id);
        admin.setRefreshToken(token);
        sqlSession.update("adminTable.updateRefreshToken", admin);
    }
}
