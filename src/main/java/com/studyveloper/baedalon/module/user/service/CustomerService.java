package com.studyveloper.baedalon.module.user.service;

import javax.persistence.EntityNotFoundException;

import com.studyveloper.baedalon.module.user.dao.CustomerRepository;
import com.studyveloper.baedalon.module.user.domain.Customer;
import com.studyveloper.baedalon.module.user.domain.CustomerStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.module.user.dto.CustomerDetails;
import com.studyveloper.baedalon.module.user.dto.CustomerEditDTO;
import com.studyveloper.baedalon.module.user.dto.CustomerSignInDTO;
import com.studyveloper.baedalon.module.user.dto.CustomerSignUpDTO;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	
	/*
	 * 고객 회원가입
	 */
	public Long signUp(@NonNull CustomerSignUpDTO customerSignUpDTO) {
		checkDuplicatedPhone(customerSignUpDTO.getPhone());
		checkDuplicatedLoginId(customerSignUpDTO.getLoginId());
		
		Customer customer = Customer.builder()
				.loginId(customerSignUpDTO.getLoginId())
				.password(passwordEncoder.encode(customerSignUpDTO.getPassword()))
				.email(customerSignUpDTO.getEmail())
				.phone(customerSignUpDTO.getPhone())
				.nickname(customerSignUpDTO.getNickname())
				.build();
		customer = customerRepository.save(customer);
		return customer.getId();
	}
	
	/*
	 * 고객 로그인
	 */
	public Customer signIn(CustomerSignInDTO customerSignInDTO) {
		//Todo : spring-security로 전환 - 고객과 업주를 어떻게 나누지?
		Customer customer = getActiveCustomer(customerSignInDTO.getLoginId());
		validatePassword(customer, customerSignInDTO.getPassword());
		return customer;
	}
	
	/*
	 * 고객 정보 수정
	 */
	public void edit(@NonNull CustomerEditDTO customerEditDTO) {		
		Customer customer = getActiveCustomer(customerEditDTO.getLoginId());
		customer.update(customerEditDTO.getPhone(), customerEditDTO.getNickname(), passwordEncoder.encode(customerEditDTO.getPassword()));
	}

	/*
	 * 고객 회원탈퇴
	 */
	public void deleteCustomer(@NonNull Long id) {
		Customer customer = getActiveCustomer(id);
		
		// Todo : 회원탈퇴 pre-condition 체크
		// 진행중인 주문이 있다거나??
		
		// Todo : 정보 삭제를 위한 탈퇴 시점 저장
		
		customer.inactivate();
	}
	
	/*
	 * 고객 찾기
	 */
	public CustomerDetails findCustomer(@NonNull Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		CustomerDetails findResult = new CustomerDetails(
											customer.getEmail(),
											customer.getPhone(),
											customer.getNickname(),
											customer.getStatus(),
											customer.getCreatedAt(),
											customer.getModifiedAt(),
											customer.getLoginId()
										);
		return findResult;
	}

//	  public List<CustomerDetails> search(Pageable pageable, SearchCondition searchCondition){
//	  	SearchCondition이 머야
//	  }
	
	/*
	 * 전화번호 중복 체크
	 */
	public void checkDuplicatedPhone(String phone) {
		boolean isDuplicated = customerRepository.existsByPhoneAndStatus(phone, CustomerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("전화번호 중복");
		}
	}
	
	/*
	 * 로그인 아이디 중복 체크
	 */
	public void checkDuplicatedLoginId(String loginId) {
		boolean isDuplicated = customerRepository.existsByLoginIdAndStatus(loginId, CustomerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("로그인 아이디 중복");
		}
	}
	
	/*
	 * 비밀번호 인증
	 */
	public void validatePassword(String loginId, String password) {
		Customer customer = getActiveCustomer(loginId);
		validatePassword(customer, password);
	}
	
	/*
	 * 비밀번호 인증
	 */
	private void validatePassword(Customer customer, String password) {
		if(!passwordEncoder.matches(password, customer.getPassword())) {
			throw new RuntimeException("비밀번호 틀림");
		}
	}
	
	/*
	 * 로그인 아이디로 활성화된 고객 찾기
	 */
	private Customer getActiveCustomer(String loginId) {
		return customerRepository.findByLoginIdAndStatus(loginId, CustomerStatus.ACTIVATED).orElseThrow(EntityNotFoundException::new);
	}
	
	/*
	 * 아이디로 활성화된 고객 찾기
	 */
	private Customer getActiveCustomer(Long id) {
		return customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
