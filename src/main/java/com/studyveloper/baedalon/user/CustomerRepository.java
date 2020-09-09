package com.studyveloper.baedalon.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public boolean existsByPhoneAndStatus(String phone, CustomerStatus status);
	public boolean existsByLoginIdAndStatus(String loginId, CustomerStatus status);
	public Optional<Customer> findByLoginIdAndStatus(String loginId, CustomerStatus status);
}
