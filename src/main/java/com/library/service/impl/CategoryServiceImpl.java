package com.library.service.impl;

import com.library.dto.CategoryDto;
import com.library.entity.Category;
import com.library.mapper.CategoryMapper;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryDto> results = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category: categories) {
            results.add(categoryMapper.toDto(category));
        }
        return results;
    }
}
