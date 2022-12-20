package com.charles.website.service.impl;

import com.charles.website.domain.Brand;
import com.charles.website.exception.BadRequestException;
import com.charles.website.exception.NotFoundException;
import com.charles.website.repository.BrandRepository;
import com.charles.website.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void create(String name) {
        if(name==null) throw new BadRequestException(400, "Name is required");

        brandRepository.save(new Brand(name));
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Brand brand = null;
        try {
            brand = brandRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(brand==null) throw new NotFoundException(404, "Brand is not found");

        brandRepository.delete(brand);
    }
}
