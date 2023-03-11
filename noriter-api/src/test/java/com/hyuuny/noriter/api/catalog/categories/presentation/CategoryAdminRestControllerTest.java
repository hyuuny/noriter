package com.hyuuny.noriter.api.catalog.categories.presentation;

import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;
import com.hyuuny.noriter.api.catalog.categories.application.CategoryService;
import com.hyuuny.noriter.api.catalog.categories.domain.CategoryRepository;
import com.hyuuny.noriter.api.common.BaseIntegrationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.stream.IntStream;

import static com.hyuuny.noriter.api.Fixtures.aCategory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class CategoryAdminRestControllerTest extends BaseIntegrationTests {

    private final String CATEGORY_REQUEST_PATH = "/api/admin/v1/categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @AfterEach
    void tearDown() {
        log.info("CategoryAdminRestControllerTest.deleteAll");
        categoryRepository.deleteAll();
    }

    @DisplayName("관리자는 카테고리를 등록할 수 있다.")
    @Test
    void createCategory() throws Exception {
        CategoryDto.Create dto = aCategory().build();

        mockMvc.perform(post(CATEGORY_REQUEST_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("code").value("20100"))
                .andExpect(jsonPath("message").value("CREATED"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value(dto.getName()))
                .andExpect(jsonPath("$.data.priorityNumber").value(dto.getPriorityNumber()))
                .andExpect(jsonPath("$.data.iconImageUrl").value(dto.getIconImageUrl()))
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.lastModifiedAt").exists())
        ;
    }

    @DisplayName("관리자는 카테고리 목록을 조회할 수 있다.")
    @Test
    void getCategories() throws Exception {
        IntStream.range(0, 11).forEach(value -> {
            CategoryDto.Create dto = aCategory()
                    .name("카테고리" + value)
                    .priorityNumber(value)
                    .build();
            categoryService.createCategory(dto);
        });

        mockMvc.perform(get(CATEGORY_REQUEST_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("20000"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("data[0].priorityNumber").value(10))
                .andExpect(jsonPath("data[1].priorityNumber").value(9))
                .andExpect(jsonPath("data[2].priorityNumber").value(8))
                .andExpect(jsonPath("data[3].priorityNumber").value(7))
                .andExpect(jsonPath("data[4].priorityNumber").value(6))
                .andExpect(jsonPath("data[5].priorityNumber").value(5))
                .andExpect(jsonPath("data[6].priorityNumber").value(4))
                .andExpect(jsonPath("data[7].priorityNumber").value(3))
                .andExpect(jsonPath("data[8].priorityNumber").value(2))
                .andExpect(jsonPath("data[9].priorityNumber").value(1))
                .andExpect(jsonPath("data[10].priorityNumber").value(0))
        ;
    }

    @DisplayName("관리자는 카테고리를 상세 조회할 수 있다.")
    @Test
    void getCategory() throws Exception {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);

        mockMvc.perform(get(CATEGORY_REQUEST_PATH + "/{id}", savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("20000"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value(dto.getName()))
                .andExpect(jsonPath("$.data.priorityNumber").value(dto.getPriorityNumber()))
                .andExpect(jsonPath("$.data.iconImageUrl").value(dto.getIconImageUrl()))
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.lastModifiedAt").exists())
        ;
    }

    @DisplayName("관리자는 카테고리 이름을 변경할 수 있다.")
    @Test
    void updateCategoryName() throws Exception {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response savedCategory = categoryService.createCategory(dto);

        CategoryDto.Update updateDto = CategoryDto.Update.builder()
                .name("가전제품")
                .iconImageUrl(dto.getIconImageUrl())
                .priorityNumber(dto.getPriorityNumber())
                .build();

        mockMvc.perform(put(CATEGORY_REQUEST_PATH + "/{id}", savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(updateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("20000"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value(updateDto.getName()))
                .andExpect(jsonPath("$.data.priorityNumber").value(dto.getPriorityNumber()))
                .andExpect(jsonPath("$.data.iconImageUrl").value(dto.getIconImageUrl()))
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.lastModifiedAt").exists());
    }

}