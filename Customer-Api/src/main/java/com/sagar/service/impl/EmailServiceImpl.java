package com.sagar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sagar.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public boolean sendMail(String to, String subject, String body) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static String getEmailBody(String password, String name) {
		return "<!DOCTYPE html>" + "<html lang='en'>" + "<head>" + "<meta charset='UTF-8'>"
				+ "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" + "<style>"
				+ "  body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }"
				+ "  .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; padding: 30px; "
				+ "              border: 1px solid #ddd; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); box-sizing: border-box; }"
				+ "  h2 { color: #4CAF50; text-align: center; font-size: 28px; }"
				+ "  p { font-size: 16px; color: #333; line-height: 1.5; }"
				+ "  .password-box { font-size: 20px; color: #007BFF; background-color: #f0f8ff; padding: 10px; "
				+ "                  border-radius: 5px; text-align: center; word-wrap: break-word; }"
				+ "  .footer { text-align: center; font-size: 14px; color: #999; margin-top: 30px; }"
				+ "  @media only screen and (max-width: 600px) {" + "    .container { padding: 20px !important; }"
				+ "    h2 { font-size: 22px !important; }" + "    p, .password-box { font-size: 16px !important; }"
				+ "  }" + "</style>" + "</head>" + "<body>" + "<div class='container'>"
				+ "  <h2>Welcome to Our Service!</h2>" + "  <p>Dear " + name + ",</p>"
				+ "  <p>Your account has been created successfully. We are excited to have you with us.</p>"
				+ "  <p><strong>Your temporary password:</strong></p>" + "  <div class='password-box'>" + password
				+ "</div>"
				+ "  <p>Please log in and change your password as soon as possible to keep your account secure.</p>"
				+ "  <p>If you have any questions, feel free to reach out to our support team.</p>"
				+ "  <div class='footer'>" + "    <p>Best regards,<br>Mca puri Upma(BBSR)</p>" + "  </div>" + "</div>"
				+ "</body>" + "</html>";

	}

}
