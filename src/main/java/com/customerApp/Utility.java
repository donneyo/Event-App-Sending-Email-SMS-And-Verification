package com.customerApp;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Properties;



import com.customerApp.EmailSetting.EmailSettingBag;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import jakarta.servlet.http.HttpServletRequest;


public class Utility {
//	public static String getSiteURL(HttpServletRequest request) {
//		String siteURL = request.getRequestURL().toString();
//		
//		return siteURL.replace(request.getServletPath(), "");
//	}
	
	public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settings) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(settings.getHost());
		mailSender.setPort(settings.getPort());
		mailSender.setUsername(settings.getUsername());
		mailSender.setPassword(settings.getPassword());
		
		Properties mailProperties = new Properties();
//		mailProperties.setProperty("mail.smtp.ssl.enable", "true");
		mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
		mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());
		
		mailSender.setJavaMailProperties(mailProperties);
		
		return mailSender;
	}
	
	
}
