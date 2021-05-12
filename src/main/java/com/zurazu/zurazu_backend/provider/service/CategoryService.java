package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.service.CategoryServiceInterface;
import com.zurazu.zurazu_backend.provider.dto.MainCategoryDTO;
import com.zurazu.zurazu_backend.provider.dto.SubCategoryDTO;
import com.zurazu.zurazu_backend.provider.repository.CategoryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface {
    private final CategoryDAO categoryDAO;
    @Override
    public Optional<List<MainCategoryDTO>> getMainCategories() {
        List<MainCategoryDTO> list = categoryDAO.getMainCategories();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<SubCategoryDTO>> getSubCategories(Integer mainIdx) {
        List<SubCategoryDTO> list;
        if(mainIdx == null) {
            // 전부 꺼내기
            list = categoryDAO.getAllSubCategories();
        } else {
            // 해당 idx만 꺼내기
            list = categoryDAO.getSubCategories(mainIdx);
        }
        return Optional.ofNullable(list);
    }
}
