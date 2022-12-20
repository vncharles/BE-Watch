package com.charles.website.controller;


import com.charles.website.domain.Order;
import com.charles.website.model.MessageResponse;
import com.charles.website.model.OrderRequest;
import com.charles.website.model.dto.OrderDTO;
import com.charles.website.service.OrderService;
import com.charles.website.utils.Authen;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        Authen.check();
        List<Order> list = orderService.getAll();
        List<OrderDTO> dtoList = list.stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrderRequest req) {
        Authen.check();
        orderService.create(req);

        return ResponseEntity.ok(new MessageResponse("Create order is success"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody OrderRequest req) {
        Authen.check();
        orderService.update(id, req);

        return ResponseEntity.ok(new MessageResponse("Update order is success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        Authen.check();
        Order order = orderService.getDetail(id);
        return ResponseEntity.ok(new OrderDTO(order));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        Authen.check();
        orderService.delete(id);

        return ResponseEntity.ok(new MessageResponse("Delete order is success"));
    }

    @PutMapping("/cancle-order")
    public ResponseEntity<?> cancleOrder(@RequestParam Long id) {
        Authen.check();
        orderService.cancleOrder(id);

        return ResponseEntity.ok(new MessageResponse("Cancle order is success"));
    }
}
