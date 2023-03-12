package com.hyuuny.noriter.api.catalog.categories.presentation;


import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;
import com.hyuuny.noriter.api.catalog.categories.application.CategoryService;
import com.hyuuny.noriter.support.common.abstraction.AbstractController;
import com.hyuuny.noriter.support.common.dto.NoriterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카테고리 API")
@RequestMapping("/api/admin/v1/categories")
@RequiredArgsConstructor
@RestController
public class CategoryAdminRestController extends AbstractController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 등록")
    @PostMapping
    public ResponseEntity<NoriterResponseDto<CategoryDto.Response>> createCategory(
            @RequestBody @Valid CategoryDto.Create dto
    ) {
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);
        return created(savedCategory);
    }

    @Operation(summary = "카테고리 목록 조회")
    @GetMapping
    public ResponseEntity<NoriterResponseDto<List<CategoryDto.Response>>> getCategories() {
        List<CategoryDto.Response> existingCategories = categoryService.getCategories();
        return ok(existingCategories);
    }

    @Operation(summary = "카테고리 상세 조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<NoriterResponseDto<CategoryDto.Response>> getCategory(@PathVariable final Long id) {
        CategoryDto.Response existingCategory = categoryService.getCategory(id);
        return ok(existingCategory);
    }

    @Operation(summary = "카테고리 수정")
    @PutMapping(value = "/{id}")
    public ResponseEntity<NoriterResponseDto<CategoryDto.Response>> updateCategory(
            @PathVariable final Long id,
            @RequestBody @Valid CategoryDto.Update dto
    ) {
        CategoryDto.Response updatedCategory = categoryService.updateCategory(id, dto);
        return ok(updatedCategory);
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable final Long id) {
        categoryService.deleteCategory(id);
        return noContent();
    }

}
