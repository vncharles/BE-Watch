package com.charles.website.controller;

import com.charles.website.domain.EStatusOrder;
import com.charles.website.model.MessageResponse;
import com.charles.website.repository.CategoryRepository;
import com.charles.website.repository.OrderRepository;
import com.charles.website.repository.ProductRepository;
import com.charles.website.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Test {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StatisticService service;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("")
    public ResponseEntity<?> test(){

        return ResponseEntity.ok(orderRepository.findAllByStatus(EStatusOrder.WAIT));
    }
}
