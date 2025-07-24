package com.sagar.service.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.sagar.dto.OrderApiRequestDto;
import com.sagar.dto.OrderApiResponseDto;
import com.sagar.dto.OrderItemDto;
import com.sagar.dto.OrdersDto;
import com.sagar.entity.Customer;
import com.sagar.entity.OrderDetails;
import com.sagar.entity.OrderItems;
import com.sagar.entity.ShippingAddress;
import com.sagar.repo.CustomerRepository;
import com.sagar.repo.OrderDetailsRepository;
import com.sagar.repo.OrderItemsRepository;
import com.sagar.repo.ShippingAddressRepository;
import com.sagar.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private CustomerRepository customerRepository;
	private OrderDetailsRepository orderDetailsRepository;
	private ShippingAddressRepository shippingAddressRepository;
	private OrderItemsRepository orderItemsRepository;

	public OrderServiceImpl(CustomerRepository customerRepository, OrderDetailsRepository orderDetailsRepository,
			ShippingAddressRepository shippingAddressRepository, OrderItemsRepository orderItemsRepository) {
		this.customerRepository = customerRepository;
		this.orderDetailsRepository = orderDetailsRepository;
		this.shippingAddressRepository = shippingAddressRepository;
		this.orderItemsRepository = orderItemsRepository;
	}

	private RazorpayClient client;

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	@Override
	public OrderApiResponseDto createOrder(OrderApiRequestDto orderApiRequestDto) throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("amount", orderApiRequestDto.getOrder().getTotalPrice() * 100);
		jsonObj.put("currency", "INR");
		jsonObj.put("receipt", orderApiRequestDto.getCustomer().getEmail());

		// Initialize Razorpay client
		this.client = new RazorpayClient(keyId, keySecret);
		com.razorpay.Order razorPayOrder = client.Orders.create(jsonObj);

		// save customer details if customer is new orElse hold old record
		Optional<Customer> customerByEmail = customerRepository
				.findByEmail(orderApiRequestDto.getCustomer().getEmail());
		Customer customer = new Customer();
		if (customerByEmail.isEmpty()) {
			BeanUtils.copyProperties(orderApiRequestDto.getCustomer(), customer);
			customer = customerRepository.save(customer);
		} else {
			customer = customerByEmail.get();
		}

		// save address
		ShippingAddress address = new ShippingAddress();
		BeanUtils.copyProperties(orderApiRequestDto.getAddress(), address);
		address.setCustomer(customer);
		address = shippingAddressRepository.save(address);

		// create order
		OrderDetails order = new OrderDetails();
		BeanUtils.copyProperties(orderApiRequestDto.getOrder(), order);
		order.setRazorPayOrderId(razorPayOrder.get("id"));
		order.setOrderStatus(razorPayOrder.get("status"));
		order.setTrackingNumber(generateOrderTrackingId());
		order.setEmail(customer.getEmail());
		order.setShippingAddress(address);
		order.setCustomer(customer);
		order = orderDetailsRepository.save(order);

		// save order Item
		List<OrderItemDto> orderItems = orderApiRequestDto.getOrderItem();
		for (OrderItemDto orderDto : orderItems) {
			OrderItems orderItem = new OrderItems();
			BeanUtils.copyProperties(orderDto, orderItem);
			orderItem.setOrderDetails(order);
			orderItemsRepository.save(orderItem);
		}

		// create and return the order status
		OrderApiResponseDto response = new OrderApiResponseDto();
		response.setOrderStatus(razorPayOrder.get("status"));
		response.setRazorpayOrderId(razorPayOrder.get("id"));
		response.setOrderTrackingNumber(generateOrderTrackingId());
		return response;
	}

	@Override
	public OrderApiResponseDto updateOrder(OrdersDto ordersDto) {
		OrderApiResponseDto response = new OrderApiResponseDto();
		Optional<OrderDetails> byId = orderDetailsRepository.findById(ordersDto.getOrderId());
		if (byId.isPresent()) {
			OrderDetails orderDetails = byId.get();
			orderDetails.setOrderStatus(ordersDto.getOrderStatus());
			OrderDetails order = orderDetailsRepository.save(orderDetails);
			response.setOrderStatus(order.getOrderStatus());
			response.setRazorpayOrderId(order.getRazorPayOrderId());
			response.setOrderTrackingNumber(generateOrderTrackingId());
		}
		return response;
	}

	@Override
	public List<OrdersDto> getAllOrder(String email) {
		List<OrderDetails> byEmail = orderDetailsRepository.findByEmail(email);

		List<OrdersDto> orderDto = new ArrayList<>();
		for (OrderDetails orderDetail : byEmail) {
			OrdersDto dto = new OrdersDto();
			BeanUtils.copyProperties(orderDetail, dto);
			orderDto.add(dto);
		}
		return orderDto;
	}

	public String generateOrderTrackingId() {
		// Get the current timestamp
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date(0));

		// Generate a random UUID for uniqueness
		String randomUUID = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

		// Combine timestamp and UUID to form the tracking ID
		return "OD_" + timestamp + "_" + randomUUID;
	}

}
