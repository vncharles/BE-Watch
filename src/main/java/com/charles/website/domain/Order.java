package com.charles.website.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data @AllArgsConstructor @NoArgsConstructor
public class Order extends BaseDomain {
    private EStatusOrder status;
    private int quantity;
    private double orderTotal;
    private String address;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
