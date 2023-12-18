package com.customerApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerApp.Dto.OtpRequest;
import com.customerApp.Dto.OtpValidationRequest;
import com.customerApp.entity.Attendees;
import com.customerApp.twilioSms.SmsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OtpRestController {

	@Autowired
	private SmsService smsService;
	
	
	//method to route OTP controller to sms service
	@PostMapping("/otp/send-otp")
	public String sendOtp(OtpRequest otpRequest, Attendees attendee,HttpServletRequest request) {
		log.info("inside sendOtp :: "+otpRequest.getUsername());
		if (smsService.sendSMS(otpRequest, attendee,request)) {
			return "OK";
		}else {
			return "Duplicate";
		}
	
	}

	
}

