package com.hyuuny.noriter.api.catalog.categories.application;

import com.hyuuny.noriter.api.catalog.categories.domain.CategoryRepository;
import com.hyuuny.noriter.api.common.BaseIntegrationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.IntStream;

import static com.hyuuny.noriter.api.Fixtures.aCategory;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CategoryServiceTest extends BaseIntegrationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @AfterEach
    void tearDown() {
        log.info("CategoryServiceTest.deleteAll");
        categoryRepository.deleteAll();
    }

    @DisplayName("카테고리를 등록할 수 있다.")
    @Test
    void createCategory() {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);

        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo(dto.getName());
        assertThat(savedCategory.getIconImageUrl()).isEqualTo(dto.getIconImageUrl());
        assertThat(savedCategory.getPriorityNumber()).isEqualTo(dto.getPriorityNumber());
        assertThat(savedCategory.getCreatedAt()).isNotNull();
        assertThat(savedCategory.getLastModifiedAt()).isNotNull();
    }

    @DisplayName("카테고리 목록을 조회할 수 있다.")
    @Test
    void getCategories() {
        IntStream.range(0, 11).forEach(value -> {
            CategoryDto.Create dto = aCategory()
                    .name("카테고리" + value)
                    .priorityNumber(value)
                    .build();
            categoryService.createCategory(dto);
        });

        List<CategoryDto.Response> categories = categoryService.getCategories();
        assertThat(categories.size()).isEqualTo(11);
    }

}