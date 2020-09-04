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
import com.studyveloper.baedalon.user.dto.OwnerSignInDTO;
import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;

@SpringBootTest
@Transactional
public class OwnerSignInTest {
	@Autowired
	private UserService userService;
	
	private static OwnerSignUpDTO tester1;
	
	@BeforeAll
	public static void setCustomer() {
		tester1 = new OwnerSignUpDTO("test@test.com","01012345678","tester1","testPwd");
	}
	
	@Test
	public void signUpSuccess() {
		//given
		Long id = userService.signUp(tester1);
		
		OwnerSignInDTO ownerSignInDTO = new OwnerSignInDTO();
		ownerSignInDTO.setEmail(tester1.getEmail());
		ownerSignInDTO.setPassword(tester1.getPassword());
		
		//when
		Owner Owner= userService.signIn(ownerSignInDTO);
		
		//then
		assertEquals(tester1.getEmail(), Owner.getEmail());
		assertEquals(tester1.getName(), Owner.getName());
		assertEquals(tester1.getPassword(), Owner.getPassword());
		assertEquals(tester1.getPhone(), Owner.getPhone());
		assertEquals(OwnerStatus.Activated, Owner.getStatus());
	}
	
	@Test
	public void signUpFailByWrongPwd() {
		//given
		Long id = userService.signUp(tester1);
		
		OwnerSignInDTO ownerSignInDTO = new OwnerSignInDTO();
		ownerSignInDTO.setEmail(tester1.getEmail());
		ownerSignInDTO.setPassword(tester1.getPassword()+"wrongPwd");
		
		//when
		Owner owner = userService.signIn(ownerSignInDTO);
		
		//then
		assertNull(owner);
	}
	
	@Test
	public void signUpFailByNonExistentEmail() {
		//given
		Long id = userService.signUp(tester1);
		
		OwnerSignInDTO ownerSignInDTO = new OwnerSignInDTO();
		ownerSignInDTO.setEmail(tester1.getEmail()+"nonExistent");
		ownerSignInDTO.setPassword(tester1.getPassword());
		
		//when, then
		assertThrows(EntityNotFoundException.class, () -> {
			userService.signIn(ownerSignInDTO);
			}	
		);
	}
}
