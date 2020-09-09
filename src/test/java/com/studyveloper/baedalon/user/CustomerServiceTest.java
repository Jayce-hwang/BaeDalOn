package com.studyveloper.baedalon.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.CustomerSignUpDTO;

@SpringBootTest
@Transactional
public class CustomerServiceTest {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	@DisplayName("고객 회원가입 성공")
	public void signUp_success() {
		// Given
		CustomerSignUpDTO customerSignUpDTO = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		
		// When
		Long id = customerService.signUp(customerSignUpDTO);
		
		// Then
		Customer customer = customerRepository.findById(id).orElseThrow();
		
		assertThat(customer)
					.isNotNull()
					.isEqualToComparingOnlyGivenFields(customerSignUpDTO, "email", "phone", "nickname", "loginId")
					.extracting("status").isEqualTo(CustomerStatus.ACTIVATED);
	}
	
	@Test
	@DisplayName("고객 회원가입 실패 : 전화번호 중복")
 	public void signUp_fail_byDuplicatedPhoneNumber() {
		// Given   
		CustomerSignUpDTO customerSignUpDTO1 = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		CustomerSignUpDTO customerSignUpDTO2 = new CustomerSignUpDTO("test2@test.com", "01011111111", "tester2", "testId2", "testPwd2");
		customerService.signUp(customerSignUpDTO1);
		
		// When, Then
		assertThatThrownBy(() -> {
					customerService.signUp(customerSignUpDTO2);
				})
			.isInstanceOf(RuntimeException.class)
			.hasMessage("전화번호 중복");
	}
	
	@Test
	@DisplayName("고객 회원가입 실패 : 로그인 아이디 중복")
	public void signUp_fail_byDuplicatedLoginId() {
		// Given
		CustomerSignUpDTO customerSignUpDTO1 = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		CustomerSignUpDTO customerSignUpDTO2 = new CustomerSignUpDTO("test2@test.com", "01022222222", "tester2", "testId1", "testPwd2");
		customerService.signUp(customerSignUpDTO1);
		
		// When, Then
		assertThatThrownBy(() -> {
					customerService.signUp(customerSignUpDTO2);
				})
			.isInstanceOf(RuntimeException.class)
			.hasMessage("로그인 아이디 중복");		
	}
}
