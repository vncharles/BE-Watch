package com.charles.website.service;

import com.charles.website.domain.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    void create(String name);

    List<Brand> getAll();

    void delete(Long id);
}
