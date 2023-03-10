package com.hyuuny.noriter.api.catalog.categories.presentation;


import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;
import com.hyuuny.noriter.api.catalog.categories.application.CategoryService;
import com.hyuuny.noriter.support.common.abstraction.AbstractController;
import com.hyuuny.noriter.support.common.dto.NoriterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
