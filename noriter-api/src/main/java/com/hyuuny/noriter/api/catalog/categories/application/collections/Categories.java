package com.hyuuny.noriter.api.catalog.categories.application.collections;


import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;
import com.hyuuny.noriter.api.catalog.categories.domain.Category;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class Categories {

    private final List<Category> categories;

    public List<CategoryDto.Response> toResponses() {
        return categories.stream()
                .map(CategoryDto.Response::new)
                .sorted(Comparator.comparing(CategoryDto.Response::getPriorityNumber).reversed())
                .toList();
    }

}
