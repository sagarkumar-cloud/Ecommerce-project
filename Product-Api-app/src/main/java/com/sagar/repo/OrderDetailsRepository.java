package com.sagar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagar.entity.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>{

}
