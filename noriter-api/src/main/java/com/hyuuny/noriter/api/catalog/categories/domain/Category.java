package com.hyuuny.noriter.api.catalog.categories.domain;

import com.hyuuny.noriter.support.common.jpa.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String iconImageUrl;

    private int priorityNumber;

    public void changeName(final String name) {
        this.name = name;
    }

    public void changeIconImageUrl(final String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }

    public void changePriorityNumber(final int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

}
