package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;

public interface PurchaseInfoServiceInterface {
    void purchaseProduct(RequestPurchaseDTO requestPurchaseDTO);
}
