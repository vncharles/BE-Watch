package com.charles.website.service;

import com.charles.website.domain.Product;
import com.charles.website.model.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    void create(ProductRequest req, MultipartFile image);

    void update(Long id, ProductRequest req, MultipartFile image);

    List<Product> getAll();

    Product getDetail(Long id);

    void delete(Long id);
}
