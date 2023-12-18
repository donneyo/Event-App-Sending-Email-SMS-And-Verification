package com.customerApp.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.customerApp.service.AttendeesService;



@RestController
public class loginRestController {
	
	@Autowired
	AttendeesService attendeesService;
	
	@PostMapping("/login/check_doescode_match")
	public String checkDoesCodeMatch(@Param("verificationCode") String verificationCode) {
		return attendeesService.doesCodeExist(verificationCode) ? "true" : "false";
	}
	
}
