package com.sagar.mapper;

import org.modelmapper.ModelMapper;

import com.sagar.dto.CustomerDto;
import com.sagar.entity.Customer;

public class CustomerMapper {

	public static final ModelMapper mapper= new ModelMapper();
	
	public static CustomerDto convertToDto(Customer customer) {
		return mapper.map(customer, CustomerDto.class);
	}
	
	public static Customer convertToEntity(CustomerDto customerDto) {
		return mapper.map(customerDto, Customer.class);
	}
}
