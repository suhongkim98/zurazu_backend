package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;

import java.util.Optional;

public interface PurchaseInfoDAOInterface {
    void registerPurchaseProductInfo(RequestPurchaseDTO requestPurchaseDTO);
}
