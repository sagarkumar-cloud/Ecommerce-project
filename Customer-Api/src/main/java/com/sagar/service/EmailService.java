package com.sagar.service;

public interface EmailService {

	boolean sendMail(String to, String subject, String body);
}
