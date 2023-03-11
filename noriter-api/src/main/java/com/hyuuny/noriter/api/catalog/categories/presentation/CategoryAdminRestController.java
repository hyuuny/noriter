package com.hyuuny.noriter.api.catalog.categories.presentation;


import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;
import com.hyuuny.noriter.api.catalog.categories.application.CategoryService;
import com.hyuuny.noriter.support.common.abstraction.AbstractController;
import com.hyuuny.noriter.support.common.dto.NoriterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/v1/categories")
@RequiredArgsConstructor
@RestController
public class CategoryAdminRestController extends AbstractController {

    private final CategoryService categoryService;

    @PostMapping(name = "카테고리 등록")
    public ResponseEntity<NoriterResponseDto<CategoryDto.Response>> createCategory(
            @RequestBody @Valid CategoryDto.Create dto
    ) {
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);
        return created(savedCategory);
    }

    @GetMapping(name = "카테고리 목록 조회")
    public ResponseEntity<NoriterResponseDto<List<CategoryDto.Response>>> getCategories() {
        List<CategoryDto.Response> existingCategories = categoryService.getCategories();
        return ok(existingCategories);
    }

    @GetMapping(value = "/{id}", name = "카테고리 상세 조회")
    public ResponseEntity<NoriterResponseDto<CategoryDto.Response>> getCategory(
            @PathVariable("id") final Long id
    ) {
        CategoryDto.Response existingCategory = categoryService.getCategory(id);
        return ok(existingCategory);
    }

    @PutMapping(value = "/{id}", name = "카테고리 수정")
    public ResponseEntity<NoriterResponseDto<CategoryDto.Response>> updateCategory(
            @PathVariable("id") final Long id,
            @RequestBody @Valid CategoryDto.Update dto
    ) {
        CategoryDto.Response updatedCategory = categoryService.updateCategory(id, dto);
        return ok(updatedCategory);
    }

}
