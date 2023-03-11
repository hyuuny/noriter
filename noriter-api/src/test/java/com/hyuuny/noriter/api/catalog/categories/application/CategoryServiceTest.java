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

    @DisplayName("카테고리를 상세 조회할 수 있다.")
    @Test
    void getCategory() {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);

        CategoryDto.Response existingCategory = categoryService.getCategory(savedCategory.getId());
        assertThat(existingCategory.getId()).isNotNull();
        assertThat(existingCategory.getName()).isEqualTo(dto.getName());
        assertThat(existingCategory.getIconImageUrl()).isEqualTo(dto.getIconImageUrl());
        assertThat(existingCategory.getPriorityNumber()).isEqualTo(dto.getPriorityNumber());
        assertThat(existingCategory.getCreatedAt()).isNotNull();
        assertThat(existingCategory.getLastModifiedAt()).isNotNull();
    }

    @DisplayName("카테고리 이름을 변경할 수 있다.")
    @Test
    void updateCategoryName() {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);

        CategoryDto.Update updateDto = CategoryDto.Update.builder()
                .name("가전제품")
                .iconImageUrl(dto.getIconImageUrl())
                .priorityNumber(dto.getPriorityNumber())
                .build();
        CategoryDto.Response updatedCategory = categoryService.updateCategory(savedCategory.getId(), updateDto);
        assertThat(updatedCategory.getId()).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo(updateDto.getName());
        assertThat(updatedCategory.getIconImageUrl()).isEqualTo(dto.getIconImageUrl());
        assertThat(updatedCategory.getPriorityNumber()).isEqualTo(dto.getPriorityNumber());
        assertThat(updatedCategory.getCreatedAt()).isNotNull();
        assertThat(updatedCategory.getLastModifiedAt()).isNotNull();
    }

    @DisplayName("카테고리 아이콘 이미지 URL을 변경할 수 있다.")
    @Test
    void updateCategoryIconImageUrl() {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);

        CategoryDto.Update updateDto = CategoryDto.Update.builder()
                .name(dto.getName())
                .iconImageUrl("https://noriter-bucket.s3.ap-northeast-2.amazonaws.com/data/image_1596187406745_1000.jpg/data/update_image_1596187406745_1000.jpg")
                .priorityNumber(dto.getPriorityNumber())
                .build();
        CategoryDto.Response updatedCategory = categoryService.updateCategory(savedCategory.getId(), updateDto);
        assertThat(updatedCategory.getId()).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo(dto.getName());
        assertThat(updatedCategory.getIconImageUrl()).isEqualTo(updatedCategory.getIconImageUrl());
        assertThat(updatedCategory.getPriorityNumber()).isEqualTo(dto.getPriorityNumber());
        assertThat(updatedCategory.getCreatedAt()).isNotNull();
        assertThat(updatedCategory.getLastModifiedAt()).isNotNull();
    }

    @DisplayName("카테고리 우선 순위를 변경할 수 있다.")
    @Test
    void updateCategoryIconPriorityNumber() {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);

        CategoryDto.Update updateDto = CategoryDto.Update.builder()
                .name(dto.getName())
                .iconImageUrl(dto.getIconImageUrl())
                .priorityNumber(1)
                .build();
        CategoryDto.Response updatedCategory = categoryService.updateCategory(savedCategory.getId(), updateDto);
        assertThat(updatedCategory.getId()).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo(dto.getName());
        assertThat(updatedCategory.getIconImageUrl()).isEqualTo(dto.getIconImageUrl());
        assertThat(updatedCategory.getPriorityNumber()).isEqualTo(updatedCategory.getPriorityNumber());
        assertThat(updatedCategory.getCreatedAt()).isNotNull();
        assertThat(updatedCategory.getLastModifiedAt()).isNotNull();
    }

    @DisplayName("카테고리를 삭제할 수 있다.")
    @Test
    void deleteCategory() {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response newCategory = categoryService.createCategory(dto);

        categoryService.deleteCategory(newCategory.getId());
        assertThat(categoryRepository.findById(newCategory.getId()).isEmpty()).isTrue();
    }

}