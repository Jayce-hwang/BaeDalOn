package com.studyveloper.baedalon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEditDTO {
	private String phone;
	private String nickname;
	private String newPassword;
	private String oldPassword;
	private Long id;
}
