package com.library.mapper;

import com.library.dto.CategoryDto;
import com.library.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setCategoryCode(entity.getCategoryCode());
        dto.setCategoryName(entity.getCategoryName());

        return dto;
    }
    public Category toEntity(CategoryDto dto) {
        Category entity = new Category();
        entity.setCategoryCode(dto.getCategoryCode());
        entity.setCategoryName(dto.getCategoryName());
        return entity;
    }
}
