package com.charles.website.controller;

import com.charles.website.domain.Brand;
import com.charles.website.model.MessageResponse;
import com.charles.website.service.BrandService;
import com.charles.website.utils.Authen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        Authen.check();

        return ResponseEntity.ok(brandService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name) {
        Authen.check();
        brandService.create(name);
        return ResponseEntity.ok(new MessageResponse("Create brand is success"));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        Authen.check();
        brandService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Delete brand is success"));
    }
}
