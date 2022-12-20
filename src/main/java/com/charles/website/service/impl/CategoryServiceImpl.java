package com.charles.website.service.impl;

import com.charles.website.domain.Category;
import com.charles.website.exception.BadRequestException;
import com.charles.website.exception.NotFoundException;
import com.charles.website.repository.CategoryRepository;
import com.charles.website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void create(String name) {
        if(name==null) throw new BadRequestException(400, "Name is required");

        categoryRepository.save(new Category(name));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Category category = null;
        try {
            category = categoryRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(category==null) throw new NotFoundException(404, "Category is not found");

        categoryRepository.delete(category);
    }
}
