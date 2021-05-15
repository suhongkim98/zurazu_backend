package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.ApplySellProductDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductDTO;
import com.zurazu.zurazu_backend.provider.dto.ApplySellProductImageDTO;
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
    public List<ApplySellProductDTO> getAllProducts() {
        return sqlSession.selectList("applySellProductTable.selectAllProducts");
    }

    @Override
    public List<ApplySellProductDTO> getAllMyProducts(int idx) {
        return sqlSession.selectList("applySellProductTable.selectAllMyProducts", idx);
    }

    @Override
    public ApplySellProductDTO getOneProduct(int idx) {
        return sqlSession.selectOne("applySellProductTable.selectOneProduct", idx);
    }

}
