package com.hyuuny.noriter.api.catalog.categories.application;

import com.hyuuny.noriter.api.catalog.categories.application.collections.Categories;
import com.hyuuny.noriter.api.catalog.categories.domain.Category;
import com.hyuuny.noriter.api.catalog.categories.domain.CategoryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryDomainService categoryDomainService;


    @Transactional
    public CategoryDto.Response createCategory(CategoryDto.Create dto) {
        Category newCategory = dto.toEntity();
        Category savedCategory = categoryDomainService.store(newCategory);
        return getCategory(savedCategory.getId());
    }

    public CategoryDto.Response getCategory(final Long id) {
        Category existingCategory = categoryDomainService.loadCategory(id);
        return new CategoryDto.Response(existingCategory);
    }

    public List<CategoryDto.Response> getCategories() {
        List<Category> existingCategories = categoryDomainService.loadCategories();
        Categories categories = new Categories(existingCategories);
        return categories.toResponses();
    }

    @Transactional
    public CategoryDto.Response updateCategory(final Long id, CategoryDto.Update dto) {
        Category existingCategory = categoryDomainService.loadCategory(id);
        dto.update(existingCategory);
        return getCategory(id);
    }

    @Transactional
    public void deleteCategory(final Long id) {
        categoryDomainService.delete(id);
    }

}
