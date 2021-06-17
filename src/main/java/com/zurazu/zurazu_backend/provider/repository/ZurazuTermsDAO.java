package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.ZurazuTermsDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.ZurazuTermsDTO;
import com.zurazu.zurazu_backend.web.dto.RequestZurazuTermsDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ZurazuTermsDAO implements ZurazuTermsDAOInterface {
    private final SqlSessionTemplate sqlSession;
    @Override
    public ZurazuTermsDTO getZurazuTerms(String type) {
        return sqlSession.selectOne("zurazuTermsTable.getZurazuTerms", type);
    }
}
