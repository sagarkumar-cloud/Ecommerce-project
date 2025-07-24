package com.sagar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagar.constant.AppConstant;
import com.sagar.dto.OrderApiRequestDto;
import com.sagar.dto.OrderApiResponseDto;
import com.sagar.dto.OrdersDto;
import com.sagar.response.ApiResponse;
import com.sagar.service.OrderService;

@RequestMapping("/ECOM/api/")
@RestController
public class OrderRestController {

	private OrderService orderService;

	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/order")
	public ResponseEntity<ApiResponse<OrderApiResponseDto>> createOrder(@RequestBody OrderApiRequestDto requestDto)
			throws Exception {

		OrderApiResponseDto order = orderService.createOrder(requestDto);
		ApiResponse<OrderApiResponseDto> response = new ApiResponse<>();

		response.setData(order);
		response.setMessage(AppConstant.ORDER_CREATED_MSG);
		response.setStatus(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/order")
	public ResponseEntity<ApiResponse<OrderApiResponseDto>> updateOrderStatus(@RequestBody OrdersDto ordersDto) {

		OrderApiResponseDto order = orderService.updateOrder(ordersDto);
		ApiResponse<OrderApiResponseDto> response = new ApiResponse<>();

		response.setData(order);
		response.setMessage(AppConstant.UPDATE_ORDER_MSG);
		response.setStatus(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/order/{email}")
	public ResponseEntity<ApiResponse<List<OrdersDto>>> getAllOrderByEmail(@PathVariable("email") String email){
		ApiResponse<List<OrdersDto>> response= new ApiResponse<>();
		List<OrdersDto> allOrder = orderService.getAllOrder(email);
		
		if(! allOrder.isEmpty()) {
			response.setData(allOrder);
			response.setMessage(AppConstant.FATCHED_ORDER_MSG);
			response.setStatus(HttpStatus.OK);
			
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		response.setData(null);
		response.setMessage(AppConstant.NO_ORDERS_FOUND_FOR_EMAIL);
		response.setStatus(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	

}
