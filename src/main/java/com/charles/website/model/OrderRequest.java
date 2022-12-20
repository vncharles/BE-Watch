package com.charles.website.model;

import com.charles.website.domain.EStatusOrder;
import lombok.Data;

@Data
public class OrderRequest {
    private Integer quantity;
    private String address;
    private Long productId;
    private Long userId;
    private String status;
}
