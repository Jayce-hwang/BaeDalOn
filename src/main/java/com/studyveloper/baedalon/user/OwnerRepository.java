package com.studyveloper.baedalon.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long>{
	public boolean existsByPhoneAndStatus(String phone, OwnerStatus status);
	public boolean existsByEmailAndStatus(String email, OwnerStatus status);
	public Optional<Owner> findByEmailAndStatus(String Email, OwnerStatus status);
}
