package com.sagar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagar.entity.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer>{

}
