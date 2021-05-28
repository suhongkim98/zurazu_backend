package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.RegisterProductDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.ColorChipDTO;
import com.zurazu.zurazu_backend.provider.dto.ProductThumbnailDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductDTO;
import com.zurazu.zurazu_backend.provider.dto.RegisterProductImageDTO;
import com.zurazu.zurazu_backend.web.dto.RequestRegisterProductDTO;
import com.zurazu.zurazu_backend.web.dto.SelectAllProductThumbnailsDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegisterProductDAO implements RegisterProductDAOInterface {
    private final SqlSessionTemplate sqlSession;

    @Override
    public void registerProduct(RequestRegisterProductDTO requestRegisterProductDTO) {
        sqlSession.insert("registerProductTable.insertProduct", requestRegisterProductDTO);
    }

    @Override
    public List<ProductThumbnailDTO> selectAllProductThumbnails(SelectAllProductThumbnailsDTO selectAllProductThumbnailsDTO) {
        return sqlSession.selectList("registerProductTable.selectAllProductThumbnailsInCategory", selectAllProductThumbnailsDTO);
    }
    @Override
    public RegisterProductDTO selectOneRegisterProduct(int idx) {
        return sqlSession.selectOne("registerProductTable.selectOneProduct", idx);
    }

    @Override
    public RegisterProductDTO selectOneRegisterProduct(String registerNumber) {
        return sqlSession.selectOne("registerProductTable.selectOneProductByRegisterNumber", registerNumber);
    }

    @Override
    public List<RegisterProductImageDTO> getAllImages(int productIdx) {
        return sqlSession.selectList("registerProductTable.getAllImages", productIdx);
    }

    @Override
    public void registerColorChip(ColorChipDTO colorChipDTO) {
        sqlSession.insert("registerProductTable.insertColorChip", colorChipDTO);
    }

    @Override
    public void insertProductImages(List<RegisterProductImageDTO> list) {
        sqlSession.insert("registerProductTable.insertProductImages", list);
    }
}
