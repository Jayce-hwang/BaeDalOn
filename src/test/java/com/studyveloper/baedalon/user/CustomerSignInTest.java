package com.studyveloper.baedalon.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.CustomerSignInDTO;
import com.studyveloper.baedalon.user.dto.CustomerSignUpDTO;

@SpringBootTest
@Transactional
public class CustomerSignInTest {
	@Autowired
	private UserService userService;
	
	private static CustomerSignUpDTO tester1;
	
	@BeforeAll
	public static void setCustomer() {
		tester1 = new CustomerSignUpDTO("test@test.com","01012345678","tester1","testPwd","testId1");
	}
	
	@Test
	public void signUpSuccess() {
		//given
		Long id = userService.signUp(tester1);
		
		CustomerSignInDTO customerSignInDTO = new CustomerSignInDTO();
		customerSignInDTO.setLoginId(tester1.getLoginId());
		customerSignInDTO.setPassword(tester1.getPassword());
		
		//when
		Customer customer = userService.signIn(customerSignInDTO);
		
		//then
		assertEquals(tester1.getEmail(), customer.getEmail());
		assertEquals(tester1.getLoginId(), customer.getLoginId());
		assertEquals(tester1.getNickname(), customer.getNickname());
		assertEquals(tester1.getPassword(), customer.getPassword());
		assertEquals(tester1.getPhone(), customer.getPhone());
		assertEquals(CustomerStatus.ACTIVATED, customer.getStatus());
	}
	
	@Test
	public void signUpFailByWrongPwd() {
		//given
		Long id = userService.signUp(tester1);
		
		CustomerSignInDTO customerSignInDTO = new CustomerSignInDTO();
		customerSignInDTO.setLoginId(tester1.getLoginId());
		customerSignInDTO.setPassword(tester1.getPassword()+"wrongPwd");
		
		//when
		Customer customer = userService.signIn(customerSignInDTO);
		
		//then
		assertNull(customer);
	}
	
	@Test
	public void signUpFailByNonExistentLoginId() {
		//given
		Long id = userService.signUp(tester1);
		
		CustomerSignInDTO customerSignInDTO = new CustomerSignInDTO();
		customerSignInDTO.setLoginId(tester1.getLoginId()+"nonExistent");
		customerSignInDTO.setPassword(tester1.getPassword());
		
		//when, then
		assertThrows(EntityNotFoundException.class, () -> {
			userService.signIn(customerSignInDTO);
			}	
		);
	}
}
