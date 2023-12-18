package com.customerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.customerApp.entity.AttendeesEmailSender;
import com.customerApp.repository.AttendeeEmailRepo;


@Service
public class AttendeeEmailService {

	
	
	@Autowired
	private AttendeeEmailRepo attendeeEmailRepo;
	
	
	public AttendeesEmailSender saveAttendee (AttendeesEmailSender attendeeEmailSender) {
		return attendeeEmailRepo.save(attendeeEmailSender);		
		
		
	}
}
