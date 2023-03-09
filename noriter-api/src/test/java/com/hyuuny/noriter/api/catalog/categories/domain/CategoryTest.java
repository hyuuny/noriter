package com.hyuuny.noriter.api.catalog.categories.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .name("생활가전")
                .iconImageUrl("https://noriter-bucket.s3.ap-northeast-2.amazonaws.com/data/image_1596187406745_1000.jpg")
                .priorityNumber(900)
                .build();
    }

    @DisplayName("카테고리 이름을 변경할 수 있다.")
    @Test
    void changeName() {
        String beforeChangeName = category.getName();
        assertThat(category.getName()).isEqualTo(beforeChangeName);

        String afterChangedName = "가공식품";
        category.changeName(afterChangedName);
        assertThat(category.getName()).isEqualTo(afterChangedName);
    }

    @DisplayName("카테고리 아이콘 이미지 URL을 변경할 수 있다.")
    @Test
    void changeIconImageUrl() {
        String beforeChangeIconImageUrl = category.getIconImageUrl();
        assertThat(category.getIconImageUrl()).isEqualTo(beforeChangeIconImageUrl);

        String afterChangedImageUrl =
                "https://https://noriter-bucket.s3.ap-northeast-2.amazonaws.com/data/changed_image_1596187406745_1000.jpg";
        category.changeIconImageUrl(afterChangedImageUrl);
        assertThat(category.getIconImageUrl()).isEqualTo(afterChangedImageUrl);
    }

}