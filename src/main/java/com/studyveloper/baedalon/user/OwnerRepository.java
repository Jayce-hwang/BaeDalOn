package com.studyveloper.baedalon.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long>{
	public List<Owner> findByEmailOrPhoneAndStatus(String email, String phone, OwnerStatus status);
	public Optional<Owner> findByEmailAndStatus(String Email, OwnerStatus status);
}
