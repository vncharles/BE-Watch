package com.charles.website.repository;

import com.charles.website.domain.EStatusOrder;
import com.charles.website.domain.Order;
import com.charles.website.domain.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);

//    @Query(value = "SELECT * FROM Orders WHERE Status LIKE '?1'", nativeQuery = true)
    List<Order> findAllByStatus(EStatusOrder status);
}
