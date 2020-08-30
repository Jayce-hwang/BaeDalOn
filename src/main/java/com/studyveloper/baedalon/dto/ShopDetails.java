package com.studyveloper.baedalon.dto;

import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.user.Owner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopDetails {
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
