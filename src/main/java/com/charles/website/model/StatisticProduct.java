package com.charles.website.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class StatisticProduct {
    private List<CategoryQuantity> listProductSold;
    private List<CategoryQuantity> listProductNotSold;
}
