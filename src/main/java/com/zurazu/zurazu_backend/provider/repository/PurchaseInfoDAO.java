package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.PurchaseInfoDAOInterface;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PurchaseInfoDAO implements PurchaseInfoDAOInterface {
    private final SqlSessionTemplate sqlSession;
    @Override
    public void registerPurchaseProductInfo(RequestPurchaseDTO requestPurchaseDTO) {
        sqlSession.insert("purchaseInfoTable.insertPurchaseInfo", requestPurchaseDTO);
    }
}
