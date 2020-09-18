package com.studyveloper.baedalon.builder;

import com.studyveloper.baedalon.module.shop.domain.ShopStatus;
import com.studyveloper.baedalon.module.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.module.shop.dto.ShopDetails;
import com.studyveloper.baedalon.module.shop.dto.ShopEditDTO;

import java.time.LocalDateTime;

public class ShopBuilder {
    public static ShopCreateDTO shopCreateDTOBuild(String address, String description, String name, String phone, Long ownerId){
        ShopCreateDTO dto = ShopCreateDTO.builder().
                address(address).
                description(description).
                name(name).
                phone(phone).
                ownerId(ownerId).
                build();
        return dto;
    }
    public static ShopCreateDTO shopCreateDTODummyBuild(){
        ShopCreateDTO dto = ShopCreateDTO.builder().
                address("가게주소").
                description("가게설명").
                name("가게이름").
                phone("010-1234-1234").
                ownerId((long)1).
                build();
        return dto;
    }

    public static ShopDetails createDetailsBuilder(Long id, Long ownerId, String name, String address, String phone, ShopStatus status, String description, LocalDateTime createdAt, LocalDateTime modifiedAt){
        ShopDetails shopDetails = ShopDetails.builder().
                id(id).
                phone(phone).
                status(status).
                ownerId(ownerId).
                name(name).
                description(description).
                address(address).
                createdAt(createdAt).
                modifiedAt(modifiedAt).
                build();
        return shopDetails;
    }
    public static ShopDetails shopDetailsDummyBuilder(){
        ShopDetails shopDetails = ShopDetails.builder().
                id((long)1).
                phone("010-1234-1234").
                ownerId((long)1).
                name("가게이름").
                description("가게설명").
                address("가게주소").
                build();
        return shopDetails;
    }

    public static ShopEditDTO shopEditDTOBuilder(Long ownerId, String phone, String name, ShopStatus status, String description, String address){
        ShopEditDTO shopEditDTO = ShopEditDTO.builder().
                phone(phone).
                ownerId(ownerId).
                name(name).
                status(status).
                description(description).
                address(address).
                build();
        return shopEditDTO;
    }

    public static ShopEditDTO shopEditDTODummyBiulder(){
        ShopEditDTO shopEditDTO = ShopEditDTO.builder().
                ownerId((long)1).
                phone("010-1234-1234").
                name("가게이름").
                description("가게설명").
                address("가게주소").
                status(ShopStatus.CLOSE).
                build();
        return shopEditDTO;
    }
    public static ShopEditDTO shopEditDTONameBiulder(String name){
        ShopEditDTO shopEditDTO = ShopEditDTO.builder().
                ownerId((long)1).
                phone("010-1234-1234").
                name(name).
                description("가게설명").
                address("가게주소").
                status(ShopStatus.CLOSE).
                build();
        return shopEditDTO;
    }
}
