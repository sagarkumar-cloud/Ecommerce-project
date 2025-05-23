package com.sagar.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDto {

	private Long productId;
	private String name;
	private String description;
	private String title;
	private BigDecimal unitPrice;
	private String imageURL;
	private Boolean active;
	private Integer unitStocks;
}
