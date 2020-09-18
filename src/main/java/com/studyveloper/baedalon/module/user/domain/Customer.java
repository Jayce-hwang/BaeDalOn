package com.studyveloper.baedalon.module.user.domain;

import static javax.persistence.EnumType.STRING;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "CUSTORMERS")
@Getter
public class Customer {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "NICKNAME")
	private String nickname;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "STATUS")
	@Enumerated(STRING)
	private CustomerStatus status;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@Column(name = "MODIFIED_AT")
	private LocalDateTime modifiedAt;

	@Column(name = "LOGIN_ID")
	private String loginId;
	
	protected Customer() {
		status = CustomerStatus.ACTIVATED;
		createdAt = LocalDateTime.now();
		modifiedAt = LocalDateTime.now();
	}

	@Builder
	public Customer(String email, String phone, String nickname, String password, String loginId) {
		this();
		this.email = email;
		this.phone = phone;
		this.nickname = nickname;
		this.password = password;
		this.loginId = loginId;
	}
	
	public void update(String phone, String nickname, String password) {
		this.phone = phone;
		this.nickname = nickname;
		this.password = password;
		this.modifiedAt = LocalDateTime.now();
	}
	
	public void inactivate() {
		this.status = CustomerStatus.INACTIVATED;
	}
}
