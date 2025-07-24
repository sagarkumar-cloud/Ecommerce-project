package com.sagar.dto;

import lombok.Data;

@Data
public class OrderApiResponseDto {

	private String razorpayOrderId;
	
	private String orderStatus;
	
	private String orderTrackingNumber;
	
}
