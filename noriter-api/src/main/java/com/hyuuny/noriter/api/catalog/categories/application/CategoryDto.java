package com.hyuuny.noriter.api.catalog.categories.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hyuuny.noriter.api.catalog.categories.domain.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


public class CategoryDto {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create {

        @NotEmpty
        private String name;

        private String iconImageUrl;

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
    public static class Update {

        @NotEmpty
        private String name;

        private String iconImageUrl;

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
    @JsonInclude(Include.NON_EMPTY)
    public static class Response {

        @NotNull
        private Long id;

        @NotEmpty
        private String name;

        private String iconImageUrl;

        private int priorityNumber;

        private LocalDateTime createdAt;

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
