package com.studyveloper.baedalon.user.dto;

import java.time.LocalDateTime;

import com.studyveloper.baedalon.user.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {
	private String email;
	private String phone;
	private String nickname;
	private String password;
	private CustomerStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String loginId;
}
