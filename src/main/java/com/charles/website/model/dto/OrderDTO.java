package com.charles.website.model.dto;

import com.charles.website.domain.EStatusOrder;
import com.charles.website.domain.Order;
import com.charles.website.domain.Product;
import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private EStatusOrder status;
    private int quantity;
    private double orderTotal;
    private String address;
    private Product product;
    private UserResponse user;

    public OrderDTO(Order order) {
        id = order.getId();
        status = order.getStatus();
        quantity = order.getQuantity();
        orderTotal = order.getOrderTotal();
        address = order.getAddress();
        product = order.getProduct();
        user = new UserResponse(order.getUser());
    }
}
