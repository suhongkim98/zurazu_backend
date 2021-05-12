package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.CategoryDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.MainCategoryDTO;
import com.zurazu.zurazu_backend.provider.dto.SubCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryDAO implements CategoryDAOInterface {
    private final SqlSessionTemplate sqlSession;

    @Override
    public List<MainCategoryDTO> getMainCategories() {
        return sqlSession.selectList("categoryTable.selectAllMainCategories");
    }

    @Override
    public List<SubCategoryDTO> getSubCategories(Integer mainIdx) {
        return sqlSession.selectList("categoryTable.selectSubCategories", mainIdx);
    }

    @Override
    public List<SubCategoryDTO> getAllSubCategories() {
        return sqlSession.selectList("categoryTable.selectAllSubCategories");
    }


}
