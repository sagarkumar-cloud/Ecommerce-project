package com.sagar.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sagar.dto.CustomerDto;
import com.sagar.dto.LoginDto;
import com.sagar.dto.ResetPasswordDto;
import com.sagar.entity.Customer;
import com.sagar.exception.BadCredentialsException;
import com.sagar.exception.UserNotFoundException;
import com.sagar.mapper.CustomerMapper;
import com.sagar.repo.CustomerRepository;
import com.sagar.service.CustomerService;
import com.sagar.service.EmailService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private EmailService emailService;

	public CustomerServiceImpl(CustomerRepository customerRepository, EmailService emailService) {
		this.customerRepository = customerRepository;
		this.emailService = emailService;
	}

	@Override
	public boolean createCustomer(CustomerDto customerDto) {
		Customer customer = CustomerMapper.convertToEntity(customerDto);
		String password = passwordGenerator();
		customer.setPassword(password);
		customer.setPasswordUpdated("No");
		Customer save = customerRepository.save(customer);

		if (save.getCustomerId() != null) {
			String subject = "Welcome! Your Account Password";
			String body = EmailServiceImpl.getEmailBody(password, customer.getName());
			emailService.sendMail(customerDto.getEmail(), subject, body);
			return true;
		}
		return false;
	}

	@Override
	public boolean forgetPassword(String email) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("This email Don't have account"));

		if (customer != null) {
			String password = passwordGenerator();
			customer.setPassword(password);
			customerRepository.save(customer);
			String subject = "Welcome! Your Password Is Forget";
			String body = EmailServiceImpl.getEmailBody(password, customer.getName());
			emailService.sendMail(email, subject, body);
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmailAvailable(String email) {
		Optional<Customer> byEmail = customerRepository.findByEmail(email);
		if (byEmail.isPresent()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean resetPassword(ResetPasswordDto resetPasswordDto) {
		Customer customer = customerRepository.findByEmail(resetPasswordDto.getEmail())
				.orElseThrow(() -> new UserNotFoundException("Invalid email"));

		if (customer != null) {
			customer.setPassword(resetPasswordDto.getConfirmPassword());
			customer.setPasswordUpdated("Yes");
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public CustomerDto login(LoginDto loginDto) {
		Customer customer = customerRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
				.orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

		if (customer != null) {
			CustomerDto customerDto = CustomerMapper.convertToDto(customer);
			return customerDto;
		}
		throw new BadCredentialsException("Invalid Email or Password !!!");
	}

	public String passwordGenerator() {

		String charpool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			int nextInt = random.nextInt(charpool.length());
			sb.append(charpool.charAt(nextInt));
		}
		return sb.toString();
	}

}
