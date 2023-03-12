package com.hyuuny.noriter.api.catalog.categories.presentation;


import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;
import com.hyuuny.noriter.api.catalog.categories.application.CategoryService;
import com.hyuuny.noriter.support.common.abstraction.AbstractController;
import com.hyuuny.noriter.support.common.dto.NoriterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카테고리 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryRestController extends AbstractController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 조회")
    @GetMapping
    public ResponseEntity<NoriterResponseDto<List<CategoryDto.Response>>> getCategories() {
        List<CategoryDto.Response> existingCategories = categoryService.getCategories();
        return ok(existingCategories);
    }

    @Operation(summary = "카테고리 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<NoriterResponseDto<CategoryDto.Response>> getCategory(
            @PathVariable("id") final Long id
    ) {
        CategoryDto.Response existingCategory = categoryService.getCategory(id);
        return ok(existingCategory);
    }

}
