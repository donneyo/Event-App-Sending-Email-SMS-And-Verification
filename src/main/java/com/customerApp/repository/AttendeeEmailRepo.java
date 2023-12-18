package com.customerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customerApp.entity.AttendeesEmailSender;

@Repository
public interface AttendeeEmailRepo extends JpaRepository<AttendeesEmailSender, Integer> {

	
}
