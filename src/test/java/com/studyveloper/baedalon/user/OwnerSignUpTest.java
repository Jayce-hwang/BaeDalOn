package com.studyveloper.baedalon.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.OwnerDetails;
import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;

@SpringBootTest
@Transactional
public class OwnerSignUpTest {
	@Autowired
	private UserService userService;
	
	private static OwnerSignUpDTO tester1;
	private static OwnerSignUpDTO tester2;
	private static OwnerSignUpDTO tester3;
	
	@BeforeAll
	public static void setCustomer() {
		tester1 = new OwnerSignUpDTO("test1@test.com","01012345678","tester1","testPwd");
		tester2 = new OwnerSignUpDTO("test2@test.com","01012345678","tester2","testPwd");
		tester3 = new OwnerSignUpDTO("test2@test.com","01087654321","tester3","testPwd");
	}
	
	@Test
	public void signUpSuccess() {
		//given, when
		Long id = userService.signUp(tester1);
		OwnerDetails ownerDetails = userService.findOwner(id);
		
		//then
		assertEquals(tester1.getEmail(), ownerDetails.getEmail());
		assertEquals(tester1.getName(), ownerDetails.getName());
		assertEquals(tester1.getPassword(), ownerDetails.getPassword());
		assertEquals(tester1.getPhone(), ownerDetails.getPhone());
		assertEquals(OwnerStatus.Activated, ownerDetails.getStatus());
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
	public void signUpFailDupEmail() {
		//given
		userService.signUp(tester2);	
		
		//when,then
		assertThrows(RuntimeException.class, () -> {
			userService.signUp(tester3);
			}
		);
	}

}