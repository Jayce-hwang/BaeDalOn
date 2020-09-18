package com.studyveloper.baedalon.module.shop.dto;

import lombok.*;


@Data
public class ShopCreateDTO {
    private Long ownerId;
    private String name;
    private String address;
    private String phone;
    private String description;

    @Builder
    public ShopCreateDTO(Long ownerId, String name, String address, String phone, String description) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
}
