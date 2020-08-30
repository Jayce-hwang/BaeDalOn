package com.studyveloper.baedalon.dto;

import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.user.Owner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopEditDTO {
    private Long id;
    private Owner owner;
    private String name;
    private String address;
    private String phone;
    private ShopStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
