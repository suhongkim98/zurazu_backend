package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.provider.dto.PurchaseProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllPurchaseLimitDTO;

import java.util.List;
import java.util.Optional;

public interface PurchaseInfoDAOInterface {
    void registerPurchaseProductInfo(RequestPurchaseDTO requestPurchaseDTO);
    List<PurchaseProductDTO> selectAllPurchaseHistory(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO);
    List<PurchaseProductDTO> selectAllPurchaseHistoryByType(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO);
    List<PurchaseProductDTO> selectAllMemberPurchaseHistory(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO);
}
