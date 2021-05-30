package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.core.repository.PurchaseInfoDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.ConfirmPurchaseDTO;
import com.zurazu.zurazu_backend.provider.dto.PurchaseProductDTO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllPurchaseLimitDTO;
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
    public List<PurchaseProductDTO> selectAllPurchaseHistory(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO) {
        return sqlSession.selectList("purchaseInfoTable.selectAllPurchaseHistory", selectAllPurchaseLimitDTO);
    }

    @Override
    public List<PurchaseProductDTO> selectAllPurchaseHistoryByType(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO) {
        return sqlSession.selectList("purchaseInfoTable.selectAllPurchaseHistory", selectAllPurchaseLimitDTO);
    }

    @Override
    public List<PurchaseProductDTO> selectAllMemberPurchaseHistory(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO) {
        return sqlSession.selectList("purchaseInfoTable.selectAllMemberPurchaseHistory", selectAllPurchaseLimitDTO);
    }

    @Override
    public PurchaseProductDTO selectOnePurchaseHistory(String orderNumber) {
        return sqlSession.selectOne("purchaseInfoTable.selectOnePurchaseHistory", orderNumber);
    }

    @Override
    public void updateConfirmPurchase(String orderNumber, boolean isConfirm) {
        ConfirmPurchaseDTO confirm = new ConfirmPurchaseDTO();
        confirm.setOrderNumber(orderNumber);
        confirm.setConfirm(isConfirm);
        sqlSession.update("purchaseInfoTable.updateConfirmPurchase", confirm);
    }
}
