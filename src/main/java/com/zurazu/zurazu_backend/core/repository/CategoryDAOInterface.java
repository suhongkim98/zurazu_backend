package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.MainCategoryDTO;
import com.zurazu.zurazu_backend.provider.dto.SubCategoryDTO;

import java.util.List;

public interface CategoryDAOInterface {
    List<MainCategoryDTO> getMainCategories();
    List<SubCategoryDTO> getSubCategories(Integer mainIdx); // 매개변수 있으면 전체 서브카테고리 출력
    List<SubCategoryDTO> getAllSubCategories();
    SubCategoryDTO getSubCategoryInfo(int idx);
}
