package com.charles.website.service;

import com.charles.website.domain.Brand;
import com.charles.website.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void create(String name);

    List<Category> getAll();

    void delete(Long id);
}
