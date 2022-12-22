package com.charles.website.repository;

import com.charles.website.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM Category WHERE id in (SELECT distinct category_id FROM Product WHERE stock > 0)", nativeQuery=true)
    List<Category> findAllProductStock();


    @Query(value = "SELECT * FROM Category WHERE id in " +
            "(SELECT distinct category_id FROM Product WHERE id in " +
            "(SELECT product_id FROM Orders WHERE status like 'SUCCESS'))", nativeQuery=true)
    List<Category> findAllProductSold();
}
