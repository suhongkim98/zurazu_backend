package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.core.service.PurchaseInfoServiceInterface;
import com.zurazu.zurazu_backend.exception.errors.NotFoundProductException;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.repository.PurchaseInfoDAO;
import com.zurazu.zurazu_backend.provider.repository.RegisterProductDAO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PurchaseInfoService implements PurchaseInfoServiceInterface {
    private final PurchaseInfoDAO purchaseInfoDAO;
    private final RegisterProductDAO registerProductDAO;

    @Override
    public void purchaseProduct(RequestPurchaseDTO requestPurchaseDTO) {
        RegisterProductDTO registerProductDTO = registerProductDAO.selectOneRegisterProduct(requestPurchaseDTO.getRegisterNumber());

        if(registerProductDTO == null) {
            //상품번호 조회가 되지 않으면 익셉션
            throw new NotFoundProductException();
        }
        requestPurchaseDTO.setRegisterProductIdx(registerProductDTO.getIdx());

        //등록 상품 상태를 입금 대기 중으로 변경
        registerProductDAO.updateRegisterProductStatus(SaleStatusType.WAITING_DEPOSIT, registerProductDTO.getIdx());

        purchaseInfoDAO.registerPurchaseProductInfo(requestPurchaseDTO);
    }
}
