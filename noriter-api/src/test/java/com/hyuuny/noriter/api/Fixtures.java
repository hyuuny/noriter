package com.hyuuny.noriter.api;


import com.hyuuny.noriter.api.catalog.categories.application.CategoryDto;

public class Fixtures {

    public static CategoryDto.Create.CreateBuilder aCategory() {
        return CategoryDto.Create.builder()
                .name("인기매물")
                .priorityNumber(1)
                .iconImageUrl("https://noriter-bucket.s3.ap-northeast-2.amazonaws.com/data/image_1596187406745_1000.jpg/data/image_1596187406745_1000.jpg")
                ;
    }

}
