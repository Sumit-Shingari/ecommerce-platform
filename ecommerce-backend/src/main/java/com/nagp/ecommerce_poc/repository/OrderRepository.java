package com.nagp.ecommerce_poc.repository;

import com.nagp.ecommerce_poc.entity.Order;
import com.nagp.ecommerce_poc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserOrderByCreatedAtDesc(User user);

}
