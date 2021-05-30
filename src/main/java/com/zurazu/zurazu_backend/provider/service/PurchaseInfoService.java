package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.enumtype.SaleStatusType;
import com.zurazu.zurazu_backend.core.service.PurchaseInfoServiceInterface;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.exception.errors.NotFoundProductException;
import com.zurazu.zurazu_backend.provider.dto.PurchaseProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.repository.PurchaseInfoDAO;
import com.zurazu.zurazu_backend.provider.repository.RegisterProductDAO;
import com.zurazu.zurazu_backend.web.dto.RequestPurchaseDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllPurchaseLimitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    @Override
    public Optional<List<PurchaseProductDTO>> selectAllPurchaseHistory(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO) {
        return Optional.ofNullable(purchaseInfoDAO.selectAllPurchaseHistory(selectAllPurchaseLimitDTO));
    }

    @Override
    public Optional<List<PurchaseProductDTO>> selectAllPurchaseHistoryByType(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO) {
        return Optional.ofNullable(purchaseInfoDAO.selectAllPurchaseHistoryByType(selectAllPurchaseLimitDTO));
    }

    @Override
    public Optional<List<PurchaseProductDTO>> selectAllMemberPurchaseHistory(SelectAllPurchaseLimitDTO selectAllPurchaseLimitDTO) {
        return Optional.ofNullable(purchaseInfoDAO.selectAllMemberPurchaseHistory(selectAllPurchaseLimitDTO));
    }

    @Override
    public void confirmPurchase(int memberIdx, String orderNumber) {
        PurchaseProductDTO purchaseProductDTO = purchaseInfoDAO.selectOnePurchaseHistory(orderNumber);
        if(purchaseProductDTO == null) {
            throw new NotFoundProductException();
        }
        //맴버 검증
        if(purchaseProductDTO.getMember().getIdx() != memberIdx) {
            throw new CustomJwtRuntimeException(); // 다른 맴버의 구매내역 업데이트는 불가능하다.
        }

        purchaseInfoDAO.updateConfirmPurchase(orderNumber, true);
    }
}
