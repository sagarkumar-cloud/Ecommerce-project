package com.sagar.service;

import java.util.List;

import com.sagar.dto.OrderApiRequestDto;
import com.sagar.dto.OrderApiResponseDto;
import com.sagar.dto.OrdersDto;

public interface OrderService {

	public OrderApiResponseDto createOrder(OrderApiRequestDto orderApiRequestDto) throws Exception;
	
	public OrderApiResponseDto updateOrder(OrdersDto ordersDto);
	
	public List<OrdersDto> getAllOrder(String email);
}
