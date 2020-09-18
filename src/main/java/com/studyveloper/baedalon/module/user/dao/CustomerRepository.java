package com.studyveloper.baedalon.module.user.dao;

import java.util.Optional;

import com.studyveloper.baedalon.module.user.domain.Customer;
import com.studyveloper.baedalon.module.user.domain.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public boolean existsByPhoneAndStatus(String phone, CustomerStatus status);
	public boolean existsByLoginIdAndStatus(String loginId, CustomerStatus status);
	public Optional<Customer> findByLoginIdAndStatus(String loginId, CustomerStatus status);
}
