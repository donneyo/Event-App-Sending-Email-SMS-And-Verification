package com.customerApp.service;


import com.customerApp.entity.Attendees;
import com.customerApp.exception.CustomerNotFoundException;
import com.customerApp.repository.AttendeeRepo;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AttendeesService {
	
	public static final int ATTENDEES_PER_PAGE = 4;

	@Autowired
	private AttendeeRepo attendeeRepo;
	

	//method to save attendees
	public Attendees saveAttendee (Attendees attendee) {
		
		return attendeeRepo.save(attendee);		
			
	}
	

	
	//method to list attendee in ascending order
		public List<Attendees> listAll(){
			return attendeeRepo.findAll(Sort.by("firstName").ascending());
		}

	
	//method to list by pagenum, field , keyword, dir etc
	public Page<Attendees> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, ATTENDEES_PER_PAGE, sort);

		if (keyword != null) {
			return attendeeRepo.findAll(keyword, pageable);
		}

		return attendeeRepo.findAll(pageable);
	}


	//method to get attendee id....
	public  Optional<Attendees> getAttendeeById(Integer id) {
	
		return attendeeRepo.findById(id);
	}


	//method to get client Id...
	public Attendees get(Integer id) throws CustomerNotFoundException {
		try {
			return attendeeRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CustomerNotFoundException("Could not find any customers with ID " + id);
		}
	}
	

	
	//method to validate code 
	public boolean doesCodeMatch(String verificationCode) {
		Attendees expectedCode =attendeeRepo.findByCode(verificationCode);
		
		
		if(expectedCode != null) {
			String newExpectedcode = expectedCode.getVerificationCode();
			String regexPattern = "\\d{6}";
			
			Pattern pattern = Pattern.compile(regexPattern);
			Matcher matcher = pattern.matcher(verificationCode);
			
			boolean codesMatch = matcher.matches() && verificationCode.equals(newExpectedcode);

			return codesMatch;
	}
		return false;
	}


	//method to check code 
	public boolean doesCodeExist(String verificationCode) {
		Attendees expectedCode =attendeeRepo.findByCode(verificationCode);
		if(expectedCode != null && verificationCode.equals(verificationCode)) {
			return true;
		}
		return false;

			
	}


}
