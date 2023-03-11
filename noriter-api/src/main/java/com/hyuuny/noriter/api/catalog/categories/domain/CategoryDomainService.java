package com.hyuuny.noriter.api.catalog.categories.domain;

import com.hyuuny.noriter.support.common.code.NoriterResponseCode;
import com.hyuuny.noriter.support.config.web.exceptions.NoriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CategoryDomainService {

    private final CategoryRepository categoryRepository;

    public Category store(Category entity) {
        return categoryRepository.save(entity);
    }

    public Category loadCategory(final Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NoriterException(NoriterResponseCode.BAD_REQUEST, id + "번 카테고리를 찾을 수 없습니다.")
        );
    }

    public List<Category> loadCategories() {
        return categoryRepository.findAll();
    }

}
