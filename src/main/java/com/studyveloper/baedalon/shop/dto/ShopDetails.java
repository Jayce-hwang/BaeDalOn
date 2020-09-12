package com.studyveloper.baedalon.shop.dto;

import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.user.Owner;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
public class ShopDetails {
    private Long id;
    private Long ownerId;
    private String name;
    private String address;
    private String phone;
    private ShopStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ShopDetails(Long id, Long ownerId, String name, String address, String phone, ShopStatus status, String description, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShopDetails that = (ShopDetails) obj;
        return Objects.equals(id, that.id);
    }
}
