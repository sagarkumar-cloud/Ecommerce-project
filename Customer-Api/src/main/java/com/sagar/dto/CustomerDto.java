package com.sagar.dto;

import lombok.Data;

@Data
public class CustomerDto {

	private String name;
	private String email;
	private Long phno;
	private String password;
	private String passwordUpdated;
}
