package com.charles.website.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Product extends BaseDomain {
    private String title;
    private String description;
    private int stock;
    private double price;
    private String image;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public boolean hasStock(int amount) {
        return (this.getStock() > 0) && (amount <= this.getStock());
    }

    public void decreaseStock(int amount) {
        this.stock -= amount;
    }

    public void increaseStock(int amount){
        this.stock += amount;
    }
}
