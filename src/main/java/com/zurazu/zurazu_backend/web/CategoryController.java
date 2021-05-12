package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.exception.errors.NotFoundCategoryException;
import com.zurazu.zurazu_backend.provider.dto.MainCategoryDTO;
import com.zurazu.zurazu_backend.provider.dto.SubCategoryDTO;
import com.zurazu.zurazu_backend.provider.service.CategoryService;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/mainCategories")
    public ResponseEntity<CommonResponse> getMainCategories() {
        List<MainCategoryDTO> list = categoryService.getMainCategories().orElseThrow(()->new NotFoundCategoryException());

        CommonResponse commonResponse = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("카테고리 조회 성공")
                .list(list)
                .build();
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
    @GetMapping("/subCategories")
    public ResponseEntity<CommonResponse> getSubCategories(@RequestParam(value = "mainIdx", required = false) Integer idx) {
        //idx가 null이라면 서브카테고리 전부 꺼내오고 널이 아니면 해당 idx에 해당하는 서브 카테고리만 꺼내온다.
        List<SubCategoryDTO> list = categoryService.getSubCategories(idx).orElseThrow(()->new NotFoundCategoryException());

        CommonResponse commonResponse = CommonResponse.builder()
                .status(HttpStatus.OK)
                .message("카테고리 조회 성공")
                .list(list)
                .build();
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
