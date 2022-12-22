package com.charles.website.repository;

import com.charles.website.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM watch_shop.product p WHERE p.stock > 0 AND p.category_id = ?1", nativeQuery = true)
    List<Product> findAllStock(Long categoryId);

    @Query(value = "SELECT * FROM watch_shop.product p WHERE p.category_id = ?1 \n" +
            "AND p.id in (SELECT o.product_id FROM watch_shop.orders o WHERE o.status like 'SUCCESS');", nativeQuery = true)
    List<Product> findAllSold(Long categoryId);
}
