package com.sagar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagar.entity.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>{

	public List<OrderDetails> findByEmail(String email);
}
