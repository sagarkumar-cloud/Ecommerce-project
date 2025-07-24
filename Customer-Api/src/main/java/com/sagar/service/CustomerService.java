package com.sagar.service;

import com.sagar.dto.CustomerDto;
import com.sagar.dto.LoginDto;
import com.sagar.dto.ResetPasswordDto;

public interface CustomerService {

	boolean createCustomer(CustomerDto customerDto);
	
	boolean forgetPassword(String email);
	
	boolean isEmailAvailable(String email);
	
	CustomerDto login(LoginDto loginDto);
		
	boolean resetPassword(ResetPasswordDto resetPasswordDto);
}
