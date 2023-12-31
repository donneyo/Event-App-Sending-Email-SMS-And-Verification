package com.customerApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerApp.service.CustomerService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	//check uniqueness method
	@PostMapping("/customers/check_email")
	public String checkDuplicateEmail(@Param("id")Integer id,@Param("email") String email) {
		return customerService.isEmailUnique(id, email) ? "OK"  : "Duplicated";
	}
	
	
}
