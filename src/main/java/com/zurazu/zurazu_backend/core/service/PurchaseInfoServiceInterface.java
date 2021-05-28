package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.provider.dto.PurchaseProductDTO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;

import java.util.List;
import java.util.Optional;

public interface PurchaseInfoServiceInterface {
    void purchaseProduct(RequestPurchaseDTO requestPurchaseDTO);
    Optional<List<PurchaseProductDTO>> selectAllPurchaseHistory();
    Optional<List<PurchaseProductDTO>> selectAllPurchaseHistoryByType(SaleStatusType type);
    Optional<List<PurchaseProductDTO>> selectAllMemberPurchaseHistory(int memberIdx);
}
