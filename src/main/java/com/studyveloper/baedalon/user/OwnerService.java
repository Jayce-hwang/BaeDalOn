package com.studyveloper.baedalon.user;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;

	/*
	 * 업주 회원가입
	 */
	public Long signUp(@NonNull OwnerSignUpDTO ownerSignUpDTO) {
		checkDuplicatedPhone(ownerSignUpDTO.getPhone());
		checkDuplicatedEmail(ownerSignUpDTO.getEmail());
		
		Owner owner = Owner.builder()
				.email(ownerSignUpDTO.getEmail())
				.phone(ownerSignUpDTO.getPhone())
				.password(passwordEncoder.encode(ownerSignUpDTO.getPassword()))
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
		Owner owner = getActiveOwner(ownerEditDTO.getEmail());
		owner.update(ownerEditDTO.getPhone(), ownerEditDTO.getName(), passwordEncoder.encode(ownerEditDTO.getPassword()));
	}

	/*
	 * 업주 회원탈퇴
	 */
	public void deleteOwner(@NonNull Long id) {
		Owner owner = getActiveOwner(id);
		
		// Todo : 회원탈퇴 pre-condition 체크
		// 진행중인 주문이 있다거나??
				
		// Todo : 정보 삭제를 위한 탈퇴 시점 저장
		
		owner.inactivate();
	}
	
	/*
	 * 업주 찾기
	 */
	public OwnerDetails findOwner(@NonNull Long id) {
		Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		
		OwnerDetails findResult = new OwnerDetails(
											owner.getEmail(),
											owner.getPhone(),
											owner.getName(),
											owner.getStatus(),
											owner.getCreatedAt(),
											owner.getModifiedAt()
										);
		return findResult;
	}
	
	/*
	 * 전화번호 중복 체크
	 */
	public void checkDuplicatedPhone(String phone) {
		boolean isDuplicated = ownerRepository.existsByPhoneAndStatus(phone, OwnerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("전화번호 중복");
		}
	}
	
	/*
	 * 이메일 중복 체크
	 */
	public void checkDuplicatedEmail(String email) {
		boolean isDuplicated = ownerRepository.existsByEmailAndStatus(email, OwnerStatus.ACTIVATED);
		
		if(isDuplicated) {
			throw new RuntimeException("이메일 중복");
		}
	}
	
	/*
	 * 비밀번호 인증
	 */
	public void validatePassword(String email, String password) {
		Owner owner = getActiveOwner(email);
		validatePassword(owner, password);
	}
	
	/*
	 * 비밀번호 인증
	 */
	private void validatePassword(Owner owner, String password) {
		if(!passwordEncoder.matches(password, owner.getPassword())) {
			throw new RuntimeException("비밀번호 틀림");
		}
	}
	
	/*
	 * 이메일로 활성화된 업주 찾기
	 */
	private Owner getActiveOwner(String email) {
		return ownerRepository.findByEmailAndStatus(email, OwnerStatus.ACTIVATED).orElseThrow(EntityNotFoundException::new);
	}
	
	/*
	 * 아이디로 활성화된 업주 찾기
	 */
	private Owner getActiveOwner(Long id) {
		return ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
