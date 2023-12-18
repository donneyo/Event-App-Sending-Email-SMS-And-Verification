package com.customerApp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.customerApp.entity.Attendees;
import com.customerApp.service.AttendeeEmailService;
import com.customerApp.service.AttendeesService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class AttendeeController {
	private String defaultRedirectURL = "redirect:/attendee_list/page/1?sortField=firstName&sortDir=asc";
	
	
		@Autowired
		AttendeeEmailService attendeeEmailService;
		
	
		@Autowired
		AttendeesService attendeesService;
		
		
	
		//Method to populate data on attendee email details
		@GetMapping("/attendee/detail/{id}")
		public String viewAttendeeDetails(@PathVariable("id") Integer id,Model model){	
				Optional<Attendees>  attendees = attendeesService.getAttendeeById(id);
				model.addAttribute("attendees", attendees);	
				return "attendee_email_details";
		}
	
		
		//controller to check doesCodematch method from service layer 
		@PostMapping("/check_Code")
		public String validateOTP(@RequestParam("verificationCode") String verificationCode,Model model) {
			boolean codesMatch = attendeesService.doesCodeMatch(verificationCode);
			if (codesMatch) {
				return "valid_code";
			}else {
					return "Invalid_code";
				}
			}
			
		//method to list attendees
		@GetMapping("/attendee_list")
		public String listFirstPage(Model model) {
			return defaultRedirectURL;
		}
		
		//sub method to list attendees
		@GetMapping("/attendee_list/page/{pageNum}")
		public String listByPage(
				@PathVariable(name = "pageNum") int pageNum, Model model,
				@Param("sortField") String sortField, @Param("sortDir") String sortDir,
				@Param("keyword") String keyword
				) {
			System.out.println("Sort Field: " + sortField);
			System.out.println("Sort Order: " + sortDir);

			Page<Attendees> page = attendeesService.listByPage(pageNum, sortField, sortDir, keyword);

			List<Attendees> listAttendees = page.getContent();

			long startCount = (pageNum - 1) * AttendeesService.ATTENDEES_PER_PAGE + 1;
			long endCount = startCount + AttendeesService.ATTENDEES_PER_PAGE - 1;
			if (endCount > page.getTotalElements()) {
				endCount = page.getTotalElements();
			}

			String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listAttendees", listAttendees);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", reverseSortDir);
			model.addAttribute("keyword", keyword);
			model.addAttribute("moduleURL", "/attendee_list");
			

			return "Attendee_list";
		}
		

		//method to save attendees
		@PostMapping("/attendee/save")
		public String saveAttendee(Attendees attendee,
				HttpServletRequest request,Model model,
				RedirectAttributes redirectAttributes)
				throws IOException, MessagingException {				
				redirectAttributes.addFlashAttribute("message", "The invite has been sent successfully.");

				return "register_success";

		}
				
		
}
