package com.studyveloper.baedalon.user;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.CustomerDetails;
import com.studyveloper.baedalon.user.dto.CustomerEditDTO;
import com.studyveloper.baedalon.user.dto.CustomerSignInDTO;
import com.studyveloper.baedalon.user.dto.CustomerSignUpDTO;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
	private final CustomerRepository customerRepository;

	/*
	 * 고객 회원가입
	 */
	public Long signUp(@NonNull CustomerSignUpDTO customerSignUpDTO) {
		checkDuplicatedPhone(customerSignUpDTO.getPhone());
		checkDuplicatedLoginId(customerSignUpDTO.getLoginId());
		
		Customer customer = Customer.builder()
				.loginId(customerSignUpDTO.getLoginId())
				.password(customerSignUpDTO.getPassword())
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
		Customer customer = customerRepository.findByLoginIdAndStatus(customerSignInDTO.getLoginId(), CustomerStatus.ACTIVATED).orElseThrow(EntityNotFoundException::new);
		
		//Todo : spring-security로 전환 - 고객과 업주를 어떻게 나누지?
		if(customer.getPassword().equals(customerSignInDTO.getPassword())) {
			return customer;
		}

		return null;
	}
	
	/*
	 * 고객 정보 수정
	 */
	public void edit(@NonNull CustomerEditDTO customerEditDTO) {		
		Customer customer = customerRepository.findById(customerEditDTO.getId()).orElseThrow(EntityNotFoundException::new);
		customer.update(
				customerEditDTO.getPhone(), 
				customerEditDTO.getNickname(), 
				customerEditDTO.getNewPassword(),
				customerEditDTO.getOldPassword());
	}

	/*
	 * 고객 회원탈퇴
	 */
	public void deleteCustomer(@NonNull Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		// Todo : 회원탈퇴 pre-condition 체크
		// 진행중인 주문이 있다거나??
		
		// Todo : 정보 삭제를 위한 탈퇴 시점 저장
		
		customer.inactivate();
	}

	public CustomerDetails findCustomer(@NonNull Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		CustomerDetails findResult = new CustomerDetails(
											customer.getEmail(),
											customer.getPhone(),
											customer.getNickname(),
											customer.getPassword(),
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
	
	public void checkDuplicatedPhone(String phone) {
		boolean isDuplicated = customerRepository.existsByPhoneAndStatus(phone, CustomerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("전화번호 중복");
		}
	}
	
	public void checkDuplicatedLoginId(String loginId) {
		boolean isDuplicated = customerRepository.existsByLoginIdAndStatus(loginId, CustomerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("로그인 아이디 중복");
		}
	}

}
