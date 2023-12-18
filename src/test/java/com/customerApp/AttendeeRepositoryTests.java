package com.customerApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.customerApp.entity.Attendees;
import com.customerApp.entity.Customer;
import com.customerApp.repository.AttendeeRepo;
import com.customerApp.repository.CustomerRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AttendeeRepositoryTests {

	@Autowired
	private AttendeeRepo repo;
	
//	@Autowired
//	private CustomerRepository repo;

	@Autowired
	private TestEntityManager entityManager;
	
//	@Test
//	public void testlistAllUsers() {
//
//		Iterable<Attendees> listUsers = repo.findAll();
//		listUsers.forEach(attendee -> System.out.println(attendee));
//	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;



		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		Page<Attendees>  page = repo.findAll(pageable);

		List<Attendees> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
}
