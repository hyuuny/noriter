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

import static com.hyuuny.noriter.api.Fixtures.aCategory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}