package com.studyveloper.baedalon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignUpDTO {
	private String email;
	private String phone;
	private String nickname;
	private String loginId;
	private String password;
}
