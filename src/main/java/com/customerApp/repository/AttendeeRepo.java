package com.customerApp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customerApp.entity.Attendees;

@Repository
public interface AttendeeRepo extends JpaRepository<Attendees, Integer> {

	@Query("SELECT a FROM Attendees a WHERE CONCAT(a.id, ' ', a.email, ' ', a.firstName, ' ',a.lastName, ' ', a.phoneNumber, ' '," + " a.verificationCode) LIKE %?1%")
	public Page<Attendees> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT a FROM Attendees a WHERE a.id = ?1")
	public Optional<Attendees> findById(Integer id);
	
	@Query("SELECT a FROM Attendees a WHERE a.verificationCode = ?1")
	public Attendees findByCode(String verificationCode);

//	public String findByCode();
	
}
