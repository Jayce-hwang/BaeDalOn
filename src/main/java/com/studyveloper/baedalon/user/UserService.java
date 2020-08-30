package com.studyveloper.baedalon.user;

import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.CustomerDetails;
import com.studyveloper.baedalon.user.dto.CustomerEditDTO;
import com.studyveloper.baedalon.user.dto.CustomerSignUpDTO;
import com.studyveloper.baedalon.user.dto.OwnerDetails;
import com.studyveloper.baedalon.user.dto.OwnerEditDTO;
import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final CustomerRepository customerRepository;
	private final OwnerRepository ownerRepository;

	/*
	 * 고객 회원가입
	 */
	public Long signUp(@NonNull CustomerSignUpDTO customerSignUpDTO) {
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
	 * 업주 회원가입
	 */
	public Long signUp(@NonNull OwnerSignUpDTO ownerSignUpDTO) {
		Owner owner = Owner.builder()
				.email(ownerSignUpDTO.getEmail())
				.password(ownerSignUpDTO.getPassword())
				.name(ownerSignUpDTO.getName())
				.build();
		owner = ownerRepository.save(owner);
		return owner.getId();
	}

//    로그인 관련 미구현	
//	  public Customer signIn(CustomerSignInDTO customerSignInDTO) {
//	  
//	  }
//	  
//	  public Owner signIn(OwnerSignInDTO ownerSignInDTO) {
//	  
//	  }
//	  
//	  public Customer signOut(CustomerSignOutDTO customerSignOutDTO) {
//	  
//	  }
//	  
//	  public Owner signOut(OwnerSignOutDTO OwnerSignOutDTO) {
//	  
//	  }
//	  
	
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
	 * 업주 정보 수정
	 */
	public void edit(@NonNull OwnerEditDTO ownerEditDTO) {
		Owner owner = ownerRepository.findById(ownerEditDTO.getId()).orElseThrow(EntityNotFoundException::new);;
		owner.update(
				ownerEditDTO.getPhone(), 
				ownerEditDTO.getName(), 
				ownerEditDTO.getNewPassword(), 
				ownerEditDTO.getOldPassword());
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

	/*
	 * 업주 회원탈퇴
	 */
	public void deleteOwner(@NonNull Long id) {
		Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		// Todo : 회원탈퇴 pre-condition 체크
		// 진행중인 주문이 있다거나??
				
		// Todo : 정보 삭제를 위한 탈퇴 시점 저장
		
		owner.inactivate();
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

	public OwnerDetails findOwner(@NonNull Long id) {
		Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		OwnerDetails findResult = new OwnerDetails(
											owner.getEmail(),
											owner.getPhone(),
											owner.getName(),
											owner.getPassword(),
											owner.getStatus(),
											owner.getCreatedAt(),
											owner.getModifiedAt()
										);
		return findResult;
	}

//	  public List<CustomerDetails> search(Pageable pageable, SearchCondition searchCondition){
//	  	SearchCondition이 머야
//	  }

}