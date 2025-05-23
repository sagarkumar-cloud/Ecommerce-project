package com.sagar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sagar.dto.ProductDto;
import com.sagar.entity.Product;
import com.sagar.entity.ProductCategory;
import com.sagar.exception.ResourceNotFoundException;
import com.sagar.repo.ProductCategoryRepository;
import com.sagar.repo.ProductRepository;
import com.sagar.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductCategoryRepository productCategoryRepository;
	private ProductRepository productRepository;

	public ProductServiceImpl(ProductCategoryRepository productCategoryRepository,
			ProductRepository productRepository) {
		this.productCategoryRepository = productCategoryRepository;
		this.productRepository = productRepository;
	}

	@Override
	public ProductDto createProduct(Long categoryId, ProductDto productDto) {
		ProductCategory pCategory = productCategoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("productCategory", "Id", categoryId));
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		product.setProductCategory(pCategory);
		Product createdProduct = productRepository.save(product);
		ProductDto productDto1 = new ProductDto();
		BeanUtils.copyProperties(createdProduct, productDto1);
		return productDto1;
	}

	@Override
	public ProductDto updateProducts(Long categoryId, Long productId, ProductDto productDto) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));

		ProductCategory category = productCategoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		if (!product.getProductCategory().getCategoryId().equals(categoryId)) {
			throw new IllegalArgumentException("Product does not belong to the specified category.");
		}

		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setTitle(productDto.getTitle());
		product.setUnitPrice(productDto.getUnitPrice());
		product.setUnitStocks(productDto.getUnitStocks());
		product.setImageURL(productDto.getImageURL());
		product.setActive(productDto.getActive());

		Product updatedProduct = productRepository.save(product);

		ProductDto responseDto = new ProductDto();
		BeanUtils.copyProperties(updatedProduct, responseDto);

		return responseDto;
	}

	@Override
	public boolean deleteProduct(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
		if (product != null) {
			productRepository.deleteById(productId);
			return true;
		}
		return false;
	}

	@Override
	public List<ProductDto> getAllProduct() {
		List<Product> all = productRepository.findAll();
		List<ProductDto> productDtos = new ArrayList<>();
		for (Product product : all) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			productDtos.add(productDto);
		}
		return productDtos;
	}

	@Override
	public ProductDto getProductBasedOnName(String productName) {
		Product product = productRepository.findByName(productName).orElseThrow();
		if (product != null) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			return productDto;
		}
		return null;
	}

	@Override
	public ProductDto getProductById(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	@Override
	public List<ProductDto> getAllProductByCategoryId(Long categoryId) {
		List<Product> products = productRepository.findByProductCategoryCategoryId(categoryId);
		List<ProductDto> productDtos = new ArrayList<>();
		for (Product prod : products) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(prod, productDto);
			productDtos.add(productDto);
		}
		return productDtos;
	}

}
