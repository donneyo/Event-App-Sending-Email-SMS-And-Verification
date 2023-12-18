package com.customerApp.twilioSms;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import com.customerApp.Utility;

import com.customerApp.Dto.OtpRequest;
import com.customerApp.Dto.OtpValidationRequest;
import com.customerApp.EmailSetting.EmailSettingBag;
import com.customerApp.EmailSetting.SettingService;
import com.customerApp.entity.Attendees;
import com.customerApp.repository.AttendeeRepo;
import com.customerApp.twilioConfig.TwilioConfig;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class SmsService {
	@Autowired 
	AttendeeRepo attendeeRepo;
	@Autowired
	private TwilioConfig twilioConfig;
	
	@Autowired 
	private SettingService settingService;
	
	
    Map<String, String> otpMap = new HashMap<>();
    
    
	public boolean sendSMS(OtpRequest otpRequest,Attendees attendee, HttpServletRequest request) {		
	try {
		PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
		PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber()); // from
		String otp = generateOTP();
		String otpMessage = "Dear friend ,You are invited to my birthday party,"
				+ " Your OTP is  " + otp + " Keep it safe for security check. Thank You.";
		Message message = Message
		        .creator(to, from,
		                otpMessage)
		        .create();
		otpMap.put(otpRequest.getUsername(), otp); 
		attendee.setVerificationCode(otp);
		attendee.setCreatedTime(new Date());
		attendeeRepo.save(attendee);
		sendVerificationEmail(request, attendee);
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;		
	}	
}
	
	
	
	//send verification method
	private void sendVerificationEmail(HttpServletRequest request, Attendees attendee) 
			throws UnsupportedEncodingException, MessagingException {
		EmailSettingBag emailSettings = settingService.getEmailSettings();
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
		
		String toAddress = attendee.getEmail();
		String subject = emailSettings.getCustomerVerifySubject();
		String content = emailSettings.getCustomerVerifyContent();
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);
		
		content = content.replace("[[name]]", attendee.getFullName());
		
		
		String verifyCode = attendee.getVerificationCode();
		content = content.replace("[[verifyCode]]", verifyCode);
		
		helper.setText(content, true);
		
		mailSender.send(message);
		
		System.out.println("to Address: " + toAddress);
		System.out.println("Verify Code: " + verifyCode);
	}	
	
	
	
	private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
