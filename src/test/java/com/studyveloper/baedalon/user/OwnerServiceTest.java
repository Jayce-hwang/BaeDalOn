package com.studyveloper.baedalon.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;

@SpringBootTest
@Transactional
public class OwnerServiceTest {
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Test
	@DisplayName("업주 회원가입 성공")
	public void signUp_success() {
		// Given
		OwnerSignUpDTO ownerSignUpDTO = new OwnerSignUpDTO("test1@test.com", "01011111111", "tester1", "testPwd1");
		
		// When
		Long id = ownerService.signUp(ownerSignUpDTO);
		
		// Then
		Owner owner = ownerRepository.findById(id).orElseThrow();
		
		assertThat(owner)
					.isNotNull()
					.isEqualToComparingOnlyGivenFields(ownerSignUpDTO, "email", "phone", "name")
					.extracting("status").isEqualTo(OwnerStatus.ACTIVATED);
	}
	
	@Test
	@DisplayName("업주 회원가입 실패 : 전화번호 중복")
 	public void signUp_fail_byDuplicatedPhoneNumber() {
		// Given   
		OwnerSignUpDTO ownerSignUpDTO1 = new OwnerSignUpDTO("test1@test.com", "01011111111", "tester1", "testPwd1");
		OwnerSignUpDTO ownerSignUpDTO2 = new OwnerSignUpDTO("test2@test.com", "01011111111", "tester2", "testPwd2");
		ownerService.signUp(ownerSignUpDTO1);
		
		// When, Then
		assertThatThrownBy(() -> {
					ownerService.signUp(ownerSignUpDTO2);
				})
			.isInstanceOf(RuntimeException.class)
			.hasMessage("전화번호 중복");
	}
	
	@Test
	@DisplayName("업주 회원가입 실패 : 이메일 중복")
	public void signUp_fail_byDuplicatedEmail() {
		// Given
		OwnerSignUpDTO ownerSignUpDTO1 = new OwnerSignUpDTO("test1@test.com", "01011111111", "tester1", "testPwd1");
		OwnerSignUpDTO ownerSignUpDTO2 = new OwnerSignUpDTO("test1@test.com", "01022222222", "tester2", "testPwd2");
		ownerService.signUp(ownerSignUpDTO1);
		
		// When, Then
		assertThatThrownBy(() -> {
					ownerService.signUp(ownerSignUpDTO2);
				})
			.isInstanceOf(RuntimeException.class)
			.hasMessage("이메일 중복");		
	}
}
