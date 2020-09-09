package com.studyveloper.baedalon.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.CustomerDetails;
import com.studyveloper.baedalon.user.dto.CustomerSignUpDTO;

@SpringBootTest
@Transactional
public class CustomerSignUpTest {
	@Autowired
	private UserService userService;
	
	private static CustomerSignUpDTO tester1;
	private static CustomerSignUpDTO tester2;
	private static CustomerSignUpDTO tester3;
	
	@BeforeAll
	public static void setCustomer() {
		tester1 = new CustomerSignUpDTO("test@test.com","01012345678","tester1","testPwd","testId1");
		tester2 = new CustomerSignUpDTO("test@test.com","01012345678","tester2","testPwd","testId2");
		tester3 = new CustomerSignUpDTO("test@test.com","01087654321","tester3","testPwd","testId2");
	}
	
	@Test
	public void signUpSuccess() {
		//given, when
		Long id = userService.signUp(tester1);
		CustomerDetails customerDetails = userService.findCustomer(id);
		
		//then
		assertEquals(tester1.getEmail(), customerDetails.getEmail());
		assertEquals(tester1.getLoginId(), customerDetails.getLoginId());
		assertEquals(tester1.getNickname(), customerDetails.getNickname());
		assertEquals(tester1.getPassword(), customerDetails.getPassword());
		assertEquals(tester1.getPhone(), customerDetails.getPhone());
		assertEquals(CustomerStatus.ACTIVATED, customerDetails.getStatus());
	}
	
	@Test
	public void signUpFailByDupPhone() {
		//given
		userService.signUp(tester1);
		
		//when,then
		assertThrows(RuntimeException.class, () -> {
			userService.signUp(tester2);
			}	
		);
	}
	
	@Test
	public void signUpFailDupLoginId() {
		//given
		userService.signUp(tester2);	
		
		//when,then
		assertThrows(RuntimeException.class, () -> {
			userService.signUp(tester3);
			}
		);
	}

}
