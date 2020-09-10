package com.studyveloper.baedalon.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.CustomerDetails;
import com.studyveloper.baedalon.user.dto.CustomerEditDTO;
import com.studyveloper.baedalon.user.dto.CustomerSignInDTO;
import com.studyveloper.baedalon.user.dto.CustomerSignUpDTO;

@SpringBootTest
@Transactional
public class CustomerServiceTest {
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		assertThat(passwordEncoder.matches(customerSignUpDTO.getPassword(), customer.getPassword())).isTrue();
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
	
	@Test
	@DisplayName("고객 로그인 성공")
	public void signIn_success() {
		// Given
		CustomerSignUpDTO customerSignUpDTO = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		CustomerSignInDTO customerSignInDTO = new CustomerSignInDTO(customerSignUpDTO.getLoginId(), customerSignUpDTO.getPassword());
		customerService.signUp(customerSignUpDTO);

		// When
		Customer customer = customerService.signIn(customerSignInDTO);
		
		// Then
		assertThat(customer)
					.isNotNull()
					.isEqualToComparingOnlyGivenFields(customerSignUpDTO, "email", "phone", "nickname", "loginId")
					.extracting("status").isEqualTo(CustomerStatus.ACTIVATED);
		assertThat(passwordEncoder.matches(customerSignUpDTO.getPassword(), customer.getPassword())).isTrue();
		
	}
	
	@Test
	@DisplayName("고객 로그인 실패 : 비밀번호 불일치")
	public void signIn_fail_byWrongPassword() {
		// Given
		CustomerSignUpDTO customerSignUpDTO = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		CustomerSignInDTO customerSignInDTO = new CustomerSignInDTO(customerSignUpDTO.getLoginId(), customerSignUpDTO.getPassword() + "wrong");
		customerService.signUp(customerSignUpDTO);
		
		// When, Then
		assertThatThrownBy(() -> {customerService.signIn(customerSignInDTO);})
				.isInstanceOf(RuntimeException.class)
				.hasMessage("비밀번호 틀림");
	}
	
	@Test
	@DisplayName("고객 로그인 실패 : 존재하지 않는 로그인 아이디")
	public void signIn_fail_byNonExistentLoginId() {
		// Given
		CustomerSignUpDTO customerSignUpDTO = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		CustomerSignInDTO customerSignInDTO = new CustomerSignInDTO(customerSignUpDTO.getLoginId() + "nonExist", customerSignUpDTO.getPassword());
		customerService.signUp(customerSignUpDTO);
		
		// When, Then
		assertThatThrownBy(() -> {
			customerService.signIn(customerSignInDTO);
		}).isInstanceOf(EntityNotFoundException.class);
	}
	
	@Test
	@DisplayName("고객 정보수정 성공")
	public void edit_success() {
		// Given
		CustomerSignUpDTO customerSignUpDTO = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		CustomerEditDTO customerEditDTO = new CustomerEditDTO("01022222222","tester2","testId1", "testPwd2");
		Long id = customerService.signUp(customerSignUpDTO);
		
		// When
		customerService.edit(customerEditDTO);
		
		// Then
		Customer customer = customerRepository.getOne(id);
		assertThat(customer)
					.isNotNull()
					.isEqualToComparingOnlyGivenFields(customerEditDTO, "phone", "nickname");
		assertThat(passwordEncoder.matches(customerEditDTO.getPassword(), customer.getPassword())).isTrue();
	}
	
	@Test
	@DisplayName("고객 회원탈퇴 성공")
	public void delete_success() {
		// Given
		CustomerSignUpDTO customerSignUpDTO = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		Long id = customerService.signUp(customerSignUpDTO);
		
		// When
		customerService.deleteCustomer(id);
		
		// Then
		Customer customer = customerRepository.getOne(id);
		assertThat(customer)
					.isNotNull()
					.hasFieldOrPropertyWithValue("status", CustomerStatus.INACTIVATED);
	}
	
	@Test
	@DisplayName("고객 검색 성공")
	public void findCustomer_success() {
		// Given
		CustomerSignUpDTO customerSignUpDTO = new CustomerSignUpDTO("test1@test.com", "01011111111", "tester1", "testId1", "testPwd1");
		Long id = customerService.signUp(customerSignUpDTO);
		
		// When
		CustomerDetails customerDetails= customerService.findCustomer(id);
		Customer customer = customerRepository.findById(id).get();
		
		// Then
		assertThat(customerDetails).isEqualToIgnoringGivenFields(customer, "id", "password");
	}
}
