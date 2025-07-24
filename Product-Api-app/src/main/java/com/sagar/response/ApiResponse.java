package com.sagar.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiResponse<T> {

	private HttpStatus status;
	private String message;
	private T data;
}
