package com.charles.website.controller;

import com.charles.website.model.MessageResponse;
import com.charles.website.service.BrandService;
import com.charles.website.service.CategoryService;
import com.charles.website.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        Authen.check();

        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name) {
        Authen.check();
        categoryService.create(name);
        return ResponseEntity.ok(new MessageResponse("Create category is success"));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        Authen.check();
        categoryService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Delete category is success"));
    }
}
