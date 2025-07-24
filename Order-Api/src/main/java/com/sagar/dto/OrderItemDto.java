package com.sagar.dto;

import lombok.Data;

@Data
public class OrderItemDto {

	private String imageURL;
	private Double unitprice;
	private Integer quantity;
	private String productName;
}
