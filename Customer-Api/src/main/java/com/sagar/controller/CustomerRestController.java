package com.sagar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sagar.dto.CustomerDto;
import com.sagar.dto.LoginDto;
import com.sagar.dto.ResetPasswordDto;
import com.sagar.response.ApiResponse;
import com.sagar.service.CustomerService;

@RestController
@RequestMapping("/ECOM/api")
public class CustomerRestController {
	
	private CustomerService customerService;
    public CustomerRestController(CustomerService customerService) {
		this.customerService = customerService;
	}
	

    @PostMapping("/customer")
    public ResponseEntity<ApiResponse<String>> createCustomer(@RequestBody CustomerDto customerDto){
    	boolean emailAvailable = customerService.isEmailAvailable(customerDto.getEmail());
    	ApiResponse<String> response= new ApiResponse<>();
    	if(!emailAvailable) {
    		response.setMessage("Duplicate email..!");
    		response.setStatus(HttpStatus.BAD_REQUEST);
    		response.setData(null);
    		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    	}
    	boolean customer = customerService.createCustomer(customerDto);
    	if(customer) {
    		response.setMessage("Register successfully..");
    		response.setStatus(HttpStatus.OK);
    		response.setData(null);
    		return new ResponseEntity<>(response,HttpStatus.OK);
    	}else {
    		response.setMessage("Register unsuccessfull");
    		response.setStatus(HttpStatus.BAD_REQUEST);
    		response.setData(null);
    		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    @PostMapping("/customer/login")
    public ResponseEntity<ApiResponse<CustomerDto>> login(@RequestBody LoginDto loginDto){
    	ApiResponse<CustomerDto> response= new ApiResponse<>();
    	CustomerDto login = customerService.login(loginDto);
    	if(login != null) {
    		response.setMessage("Login success");
    		response.setStatus(HttpStatus.OK);
    		response.setData(login);
    		return new ResponseEntity<>(response,HttpStatus.OK);
    	}else {
    		response.setMessage("Login unsuccess..!!");
    		response.setStatus(HttpStatus.BAD_REQUEST);
    		response.setData(null);
    		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    
    @PostMapping("/customer/forgetPwd")
    public ResponseEntity<ApiResponse<String>> forgetPwd(@RequestParam("email") String email){
    	boolean emailAvailable = customerService.isEmailAvailable(email);
    	ApiResponse<String> response= new ApiResponse<>();
    	if(!emailAvailable) {
    		boolean forgetPassword = customerService.forgetPassword(email);
    		if(forgetPassword) {
    			response.setMessage("Forget pwd success,Check your email.");
        		response.setStatus(HttpStatus.OK);
        		response.setData(null);
        		return new ResponseEntity<>(response,HttpStatus.OK);
    		}
    	}
    		response.setMessage("This email has not customer.");
    		response.setStatus(HttpStatus.OK);
    		response.setData(null);
    		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping("/customer/resetPwd")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto){
    	ApiResponse<String> response= new ApiResponse<>();
    	boolean resetPassword = customerService.resetPassword(resetPasswordDto);
    	if(resetPassword) {
    		response.setMessage("Passwors reset sccessfully.");
    		response.setStatus(HttpStatus.OK);
    		response.setData(null);
    		return new ResponseEntity<>(response,HttpStatus.OK);
    	}else {
    		response.setMessage("Passwors reset unsccessfully.");
    		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    		response.setData(null);
    		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
}
