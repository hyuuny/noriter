package com.hyuuny.noriter.api.catalog.categories.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hyuuny.noriter.api.catalog.categories.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


public class CategoryDto {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Schema(name = "CategoryDto.Create", description = "카테고리 등록")
    public static class Create {

        @Schema(description = "카테고리명", example = "생활가전", requiredMode = REQUIRED)
        private String name;

        @Schema(description = "아이콘 이미지 URL", example = "https://noriter-bucket.s3.ap-northeast-2.amazonaws.com/data/image_1596187406745_1000.jpg", requiredMode = NOT_REQUIRED)
        private String iconImageUrl;

        @Schema(description = "우선순위(desc)", example = "900", requiredMode = NOT_REQUIRED)
        private int priorityNumber;

        public Category toEntity() {
            return Category.builder()
                    .name(this.name)
                    .iconImageUrl(this.iconImageUrl)
                    .priorityNumber(this.priorityNumber)
                    .build();
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Schema(name = "CategoryDto.Update", description = "카테고리 수정")
    public static class Update {

        @Schema(description = "카테고리명", example = "생활가전", requiredMode = REQUIRED)
        private String name;

        @Schema(description = "아이콘 이미지 URL", example = "https://noriter-bucket.s3.ap-northeast-2.amazonaws.com/data/image_1596187406745_1000.jpg", requiredMode = NOT_REQUIRED)
        private String iconImageUrl;

        @Schema(description = "우선순위(desc)", example = "900", requiredMode = NOT_REQUIRED)
        private int priorityNumber;

        public void update(Category entity) {
            entity.changeName(this.name);
            entity.changeIconImageUrl(this.iconImageUrl);
            entity.changePriorityNumber(this.priorityNumber);
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "CategoryDto.Response", description = "카테고리 상세")
    @JsonInclude(Include.NON_EMPTY)
    public static class Response {

        @Schema(description = "아이디", example = "1", requiredMode = REQUIRED)
        private Long id;

        @Schema(description = "카테고리명", example = "생활가전", requiredMode = REQUIRED)
        private String name;

        @Schema(description = "아이콘 이미지 URL", example = "https://noriter-bucket.s3.ap-northeast-2.amazonaws.com/data/image_1596187406745_1000.jpg", requiredMode = NOT_REQUIRED)
        private String iconImageUrl;

        @Schema(description = "우선순위(desc)", example = "900", requiredMode = NOT_REQUIRED)
        private int priorityNumber;

        @Schema(description = "생성일", requiredMode = REQUIRED)
        private LocalDateTime createdAt;

        @Schema(description = "마지막 수정일", requiredMode = REQUIRED)
        private LocalDateTime lastModifiedAt;

        public Response(Category entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.iconImageUrl = entity.getIconImageUrl();
            this.priorityNumber = entity.getPriorityNumber();
            this.createdAt = entity.getCreatedAt();
            this.lastModifiedAt = entity.getLastModifiedAt();
        }

    }


}
