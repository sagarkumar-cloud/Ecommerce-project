package com.sagar.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderApiRequestDto {

	private CustomerDto customer;
	
	private AddressDto address;
	
	private OrdersDto order;
	
	private List<OrderItemDto> orderItem;
}
