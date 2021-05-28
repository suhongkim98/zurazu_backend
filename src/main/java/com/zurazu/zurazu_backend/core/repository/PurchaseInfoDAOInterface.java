package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.provider.dto.PurchaseProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;

import java.util.List;
import java.util.Optional;

public interface PurchaseInfoDAOInterface {
    void registerPurchaseProductInfo(RequestPurchaseDTO requestPurchaseDTO);
    List<PurchaseProductDTO> selectAllPurchaseHistory();
    List<PurchaseProductDTO> selectAllPurchaseHistoryByType(SaleStatusType type);
    List<PurchaseProductDTO> selectAllMemberPurchaseHistory(int memberIdx);
}
