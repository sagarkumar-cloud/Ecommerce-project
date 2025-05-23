package com.sagar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagar.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
