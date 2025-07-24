package com.sagar.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagar.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
