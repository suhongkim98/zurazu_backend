package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.enumtype.ApplySellStatusType;
import com.zurazu.zurazu_backend.core.repository.ApplySellProductDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductDTO;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductImageDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllLimitDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApplySellProductDAO implements ApplySellProductDAOInterface {
    private final SqlSessionTemplate sqlSession;

    @Override
    public void registerProduct(ApplySellProductDTO applySellProductDTO) {
        sqlSession.insert("applySellProductTable.insertProduct", applySellProductDTO);
    }

    @Override
    public void registerProductImages(List<ApplySellProductImageDTO> list) {
        sqlSession.insert("applySellProductTable.insertProductImages", list);
    }

    @Override
    public List<ApplySellProductDTO> getAllProducts(int offset, int limit) {
        SelectAllLimitDTO dto = new SelectAllLimitDTO();
        dto.setOffset(offset);
        dto.setLimit(limit);
        return sqlSession.selectList("applySellProductTable.selectAllProducts", dto);
    }

    @Override
    public List<ApplySellProductDTO> getAllMyProducts(int idx, int offset, int limit) {
        SelectAllLimitDTO dto = new SelectAllLimitDTO();
        dto.setOffset(offset);
        dto.setLimit(limit);
        dto.setIdx(idx);
        return sqlSession.selectList("applySellProductTable.selectAllMyProducts", dto);
    }

    @Override
    public ApplySellProductDTO getOneProduct(int idx) {
        return sqlSession.selectOne("applySellProductTable.selectOneProduct", idx);
    }

    @Override
    public List<ApplySellProductImageDTO> getAllProductImages(int productIdx) {
        return sqlSession.selectList("applySellProductTable.selectAllImages", productIdx);
    }

    @Override
    public void updateProductSaleStatus(ApplySellStatusType type, int productIdx) {
        ApplySellProductDTO dto = new ApplySellProductDTO();
        dto.setIdx(productIdx);
        dto.setSaleStatus(type);
        sqlSession.update("applySellProductTable.updateSaleStatus", dto);
    }

}
