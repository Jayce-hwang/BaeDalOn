package com.studyveloper.baedalon.shop.dto;

import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.user.Owner;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ShopEditDTO {
    private Long ownerId;
    private String name;
    private String address;
    private String phone;
    private ShopStatus status;
    private String description;

    @Builder
    public ShopEditDTO(Long ownerId, String name, String address, String phone, ShopStatus status, String description) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.description = description;
    }
}
