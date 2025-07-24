package com.sagar.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrdersDto {
   
	private Integer orderId;
	private String trackingNumber;
	private Integer totalQuantity;
	private Double totalPrice;
	private String orderStatus;
	private String email;
	private LocalDate deliveryDate;
	private String paymentStatus;
	private String razorPayOrderId;
	private String razorPayPaymentId;
	private String invoiceURL;
}


