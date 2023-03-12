package com.hyuuny.noriter.api.catalog.categories.presentation;

import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;
import com.hyuuny.noriter.api.catalog.categories.application.CategoryService;
import com.hyuuny.noriter.api.catalog.categories.domain.CategoryRepository;
import com.hyuuny.noriter.api.common.BaseIntegrationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.stream.IntStream;

import static com.hyuuny.noriter.api.Fixtures.aCategory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class CategoryRestControllerTest extends BaseIntegrationTests {

    private final String CATEGORY_REQUEST_PATH = "/api/v1/categories";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        log.info("CategoryRestControllerTest.deleteAll");
        categoryRepository.deleteAll();
    }

    @DisplayName("회원은 카테고리 목록을 조회할 수 있다.")
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

    @DisplayName("회원을 카테고리를 상세 조회할 수 있다.")
    @Test
    void getCategory() throws Exception {
        CategoryDto.Create dto = aCategory().build();
        CategoryDto.Response newCategory = categoryService.createCategory(dto);

        mockMvc.perform(get(CATEGORY_REQUEST_PATH + "/{id}", newCategory.getId())
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

}