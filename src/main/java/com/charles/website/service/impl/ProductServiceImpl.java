package com.charles.website.service.impl;

import com.charles.website.domain.Brand;
import com.charles.website.domain.Category;
import com.charles.website.domain.Product;
import com.charles.website.exception.BadRequestException;
import com.charles.website.exception.NotFoundException;
import com.charles.website.model.ProductRequest;
import com.charles.website.repository.BrandRepository;
import com.charles.website.repository.CategoryRepository;
import com.charles.website.repository.ProductRepository;
import com.charles.website.service.CategoryService;
import com.charles.website.service.FileService;
import com.charles.website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Override
    public void create(ProductRequest req, MultipartFile image) {
        if(req.getTitle()==null) throw new BadRequestException(400, "Title is required");
        if(req.getDescription()==null) throw new BadRequestException(400, "Description is required");
        if(req.getStock()==null) throw new BadRequestException(400, "Stock is required");
        if(req.getPrice()==null) throw new BadRequestException(400, "Price is required");
        if(req.getBrandId()==null) throw new BadRequestException(400, "Brand is required");
        if(req.getCategoryId()==null) throw new BadRequestException(400, "Category is required");
        if(image==null) throw new BadRequestException(400, "Image is required");
        Brand brand = null;
        try {
            brand = brandRepository.findById(req.getBrandId()).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(brand==null) throw new NotFoundException(404, "Brand is not found");

        Category category = null;
        try {
            category = categoryRepository.findById(req.getCategoryId()).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(category==null) throw new NotFoundException(404, "Category is not found");

        String imageName = fileService.storeFile(image);

        Product product = new Product(req.getTitle(), req.getDescription(), req.getStock(), req.getPrice(), imageName, brand, category);

        productRepository.save(product);
    }

    @Override
    public void update(Long id, ProductRequest req, MultipartFile image) {
        Product product = null;
        try {
            product = productRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(product==null) throw new NotFoundException(404, "Product is not found");

        if(req.getTitle()!=null) {
            product.setTitle(req.getTitle());
        }

        if(req.getDescription()!=null) {
            product.setDescription(req.getDescription());
        }

        if(req.getStock()!=null) {
            product.setStock(req.getStock());
        }

        if(req.getPrice()!=null) {
            product.setPrice(req.getPrice());
        }

        if(req.getBrandId()!=null) {
            Brand brand = null;
            try {
                brand = brandRepository.findById(req.getBrandId()).get();
            } catch (Exception ex) {ex.printStackTrace();}
            if(brand==null) throw new NotFoundException(404, "Brand is not found");

            product.setBrand(brand);
        }

        if(req.getCategoryId()!=null) {
            Category category = null;
            try {
                category = categoryRepository.findById(req.getCategoryId()).get();
            } catch (Exception ex) {ex.printStackTrace();}
            if(category==null) throw new NotFoundException(404, "Category is not found");

            product.setCategory(category);
        }

        if(image!=null) {
            String imageName = fileService.storeFile(image);
            product.setImage(imageName);
        }

        productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getDetail(Long id) {
        Product product = null;
        try {
            product = productRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(product==null) throw new NotFoundException(404, "Product is not found");

        return product;
    }

    @Override
    public void delete(Long id) {
        Product product = null;
        try {
            product = productRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(product==null) throw new NotFoundException(404, "Product is not found");

        productRepository.delete(product);
    }
}
