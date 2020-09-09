package com.studyveloper.baedalon.user;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studyveloper.baedalon.user.dto.OwnerDetails;
import com.studyveloper.baedalon.user.dto.OwnerEditDTO;
import com.studyveloper.baedalon.user.dto.OwnerSignInDTO;
import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService {
	private final OwnerRepository ownerRepository;

	/*
	 * 업주 회원가입
	 */
	public Long signUp(@NonNull OwnerSignUpDTO ownerSignUpDTO) {
		checkDuplicatedPhone(ownerSignUpDTO.getPhone());
		checkDuplicatedEmail(ownerSignUpDTO.getEmail());
		
		Owner owner = Owner.builder()
				.email(ownerSignUpDTO.getEmail())
				.phone(ownerSignUpDTO.getPhone())
				.password(ownerSignUpDTO.getPassword())
				.name(ownerSignUpDTO.getName())
				.build();
		owner = ownerRepository.save(owner);
		return owner.getId();
	}

	/*
	 * 업주 로그인
	 */
	public Owner signIn(OwnerSignInDTO ownerSignInDTO) {
		Owner owner = getActiveOwner(ownerSignInDTO.getEmail());
		validatePassword(owner, ownerSignInDTO.getPassword());
		return owner;	  
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
	 * 업주 회원탈퇴
	 */
	public void deleteOwner(@NonNull Long id) {
		Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		// Todo : 회원탈퇴 pre-condition 체크
		// 진행중인 주문이 있다거나??
				
		// Todo : 정보 삭제를 위한 탈퇴 시점 저장
		
		owner.inactivate();
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
	
	public void checkDuplicatedPhone(String phone) {
		boolean isDuplicated = ownerRepository.existsByPhoneAndStatus(phone, OwnerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("전화번호 중복");
		}
	}
	
	public void checkDuplicatedEmail(String email) {
		boolean isDuplicated = ownerRepository.existsByEmailAndStatus(email, OwnerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("이메일 중복");
		}
	}
	
	public void validatePassword(String email, String password) {
		Owner owner = getActiveOwner(email);
		validatePassword(owner, password);
	}
	
	private void validatePassword(Owner owner, String password) {
		if(!owner.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호 틀림");
		}
	}
	
	private Owner getActiveOwner(String email) {
		return ownerRepository.findByEmailAndStatus(email, OwnerStatus.ACTIVATED).orElseThrow(EntityNotFoundException::new);
	}
}
