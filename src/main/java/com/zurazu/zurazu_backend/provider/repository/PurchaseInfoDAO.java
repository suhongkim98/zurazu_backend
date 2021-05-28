package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.core.repository.PurchaseInfoDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.PurchaseProductDTO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PurchaseInfoDAO implements PurchaseInfoDAOInterface {
    private final SqlSessionTemplate sqlSession;
    @Override
    public void registerPurchaseProductInfo(RequestPurchaseDTO requestPurchaseDTO) {
        sqlSession.insert("purchaseInfoTable.insertPurchaseInfo", requestPurchaseDTO);
    }

    @Override
    public List<PurchaseProductDTO> selectAllPurchaseHistory() {
        return sqlSession.selectList("purchaseInfoTable.selectAllPurchaseHistory");
    }

    @Override
    public List<PurchaseProductDTO> selectAllPurchaseHistoryByType(SaleStatusType type) {
        return sqlSession.selectList("purchaseInfoTable.selectAllPurchaseHistory", type);
    }

    @Override
    public List<PurchaseProductDTO> selectAllMemberPurchaseHistory(int memberIdx) {
        return sqlSession.selectList("purchaseInfoTable.selectAllMemberPurchaseHistory", memberIdx);
    }
}
