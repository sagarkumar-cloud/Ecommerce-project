package com.sagar.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiResponse<T> {

	private String message;
	private HttpStatus status;
	private T data;
}
