package com.charles.website.service;

import com.charles.website.domain.Order;
import com.charles.website.domain.Product;
import com.charles.website.model.OrderRequest;
import com.charles.website.model.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface OrderService {
    void create(OrderRequest req);

    void update(Long id, OrderRequest req);

    void cancleOrder(Long id);

    List<Order> getAll();

    Order getDetail(Long id);

    void delete(Long id);
}
