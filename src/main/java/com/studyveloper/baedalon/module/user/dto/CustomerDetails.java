package com.studyveloper.baedalon.module.user.dto;

import java.time.LocalDateTime;

import com.studyveloper.baedalon.module.user.domain.CustomerStatus;

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
	private CustomerStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String loginId;
}
