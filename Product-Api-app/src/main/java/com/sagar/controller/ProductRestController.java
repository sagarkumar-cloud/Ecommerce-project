package com.sagar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sagar.dto.CategoryDto;
import com.sagar.dto.ProductDto;
import com.sagar.response.ApiResponse;
import com.sagar.service.CategoryService;
import com.sagar.service.ProductService;

@RequestMapping("/ECOM/api")
@RestController
@CrossOrigin
public class ProductRestController {

	private CategoryService categoryService;
	private ProductService productService;

	public ProductRestController(CategoryService categoryService, ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@GetMapping("/category")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategory() {
		ApiResponse<List<CategoryDto>> response = new ApiResponse<>();
		List<CategoryDto> allCategory = categoryService.getAllCategory();

		if (!allCategory.isEmpty()) {
			response.setStatus(HttpStatus.OK);
			response.setMessage("Fetched category data.");
			response.setData(allCategory);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage("Not Fetch the category data.");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/ECOM/api/product/mobiles
	@GetMapping("/products/{productName}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getProductBasedOnName(
			              @PathVariable("productName") String productName) {
		ApiResponse<List<ProductDto>> response = new ApiResponse<>();
		List<ProductDto> productBasedOnName = productService.getProductBasedOnName(productName);
		if (!productBasedOnName.isEmpty()) {
			response.setStatus(HttpStatus.OK);
			response.setMessage("Fetched product by name");
			response.setData(productBasedOnName);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setMessage("not Fetched the product by name");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	// http://localhost:8080/ECOM/api/category/product/1
	@GetMapping("/category/product/{categoryId}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProductsByCategoryId(
			@PathVariable("categoryId") Long categoryId) {
		ApiResponse<List<ProductDto>> response = new ApiResponse<>();
		List<ProductDto> allProductByCategoryId = productService.getAllProductByCategoryId(categoryId);
		if (!allProductByCategoryId.isEmpty()) {
			response.setStatus(HttpStatus.OK);
			response.setMessage("Fetched product by Id");
			response.setData(allProductByCategoryId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage("not Fetched the product by Id");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// http://localhost:8080/ECOM/api/product/1
	@GetMapping("/product/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> getProductByProductId(@PathVariable("productId") Long productId) {
		ApiResponse<ProductDto> response = new ApiResponse<>();
		ProductDto productById = productService.getProductById(productId);
		if (productById != null) {
			response.setStatus(HttpStatus.OK);
			response.setMessage("product fetched");
			response.setData(productById);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage("product not fetched");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// -------------------------------Not used method E-commerce
	// project--------------------------------------------//

	@PutMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> updateCatogery(@PathVariable("categoryId") Long categoryId,
			@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryDto), HttpStatus.OK);
	}

	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
		boolean deleteCategory = categoryService.deleteCategory(categoryId);
		if (deleteCategory)
			return new ResponseEntity<>("Category deleted..!", HttpStatus.OK);
		return new ResponseEntity<>("Category not deleted..!", HttpStatus.BAD_REQUEST);
	}

	// http://localhost:8080/ECOM/api/product/1
	@PostMapping("/product/{categoryId}")
	public ResponseEntity<ProductDto> createProduct(@PathVariable("categoryId") Long categoryId,
			@RequestBody ProductDto productDto) {
		ProductDto product = productService.createProduct(categoryId, productDto);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}

	// http://localhost:8080/ECOM/api/product/1/1
	@PutMapping("/product/{categoryId}/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable("categoryId") Long categoryId,
			@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {
		ProductDto updateProducts = productService.updateProducts(categoryId, productId, productDto);
		return new ResponseEntity<>(updateProducts, HttpStatus.OK);
	}
}
