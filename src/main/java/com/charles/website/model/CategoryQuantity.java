package com.charles.website.model;

import com.charles.website.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CategoryQuantity {
    private Category category;
    private int quantity;
}
