package com.studyveloper.baedalon.user.dto;

import java.time.LocalDateTime;

import com.studyveloper.baedalon.user.OwnerStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDetails {
	private String email;
	private String phone;
	private String name;
	private OwnerStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
