package com.studyveloper.baedalon.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerEditDTO {
	private String email;
    private String phone;
    private String name;
    private String password;
}
