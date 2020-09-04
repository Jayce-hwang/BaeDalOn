package com.studyveloper.baedalon.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public List<Customer> findByLoginIdOrPhoneAndStatus(String loginId, String phone, CustomerStatus status);
}
