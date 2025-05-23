package com.sagar.service;

import java.util.List;

import com.sagar.dto.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategory();
	
	CategoryDto updateCategory(Long categoryId,CategoryDto categoryDto);
	
	boolean deleteCategory(Long categoryId);
}
