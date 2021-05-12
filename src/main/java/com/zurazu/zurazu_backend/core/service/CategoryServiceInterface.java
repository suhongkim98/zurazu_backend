package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.provider.dto.MainCategoryDTO;
import com.zurazu.zurazu_backend.provider.dto.SubCategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryServiceInterface {
    Optional<List<MainCategoryDTO>> getMainCategories();
    Optional<List<SubCategoryDTO>> getSubCategories(Integer mainIdx);
}
