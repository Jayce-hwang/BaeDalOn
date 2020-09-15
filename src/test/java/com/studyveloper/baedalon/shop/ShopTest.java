package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.builder.ShopBuilder;
import com.studyveloper.baedalon.user.OwnerRepository;
import com.studyveloper.baedalon.shop.ShopService;
import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.shop.dto.ShopDetails;
import com.studyveloper.baedalon.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.user.OwnerRepository;
import com.studyveloper.baedalon.user.OwnerService;
import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;


@SpringBootTest
@Transactional
public class ShopTest {
    @Autowired
    private ShopService shopService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private OwnerRepository ownerRepository;


    @DisplayName("가게생성 성공")
    @Test
    public void create_shop_success(){
        OwnerSignUpDTO ownerSignUpDTO = new OwnerSignUpDTO("test1@test.com", "01011111111", "tester1", "testPwd1");
        Long id = ownerService.signUp(ownerSignUpDTO);
        Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        ShopCreateDTO shopCreateDTO = ShopBuilder.shopCreateDTODummyBuild();
        Long shopId = shopService.createShop(shopCreateDTO);
        Assertions.assertThat(shopId).isNotNull();
    }

    @DisplayName("가게생성 업주당 1가게 조건 실패")
    @Test
    public void create_shop_fail(){
        OwnerSignUpDTO ownerSignUpDTO = new OwnerSignUpDTO("test1@test.com", "01011111111", "tester1", "testPwd1");
        Long id = ownerService.signUp(ownerSignUpDTO);
        Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        ShopCreateDTO shopCreateDTO = ShopBuilder.shopCreateDTODummyBuild();
        Long shopId = shopService.createShop(shopCreateDTO);
        Assertions.assertThat(shopId).isNotNull();

        shopId = shopService.createShop(shopCreateDTO);
        Assertions.assertThat(shopId).isNull();
    }

    @DisplayName("가게 정보수정 성공")
    @Test
    public void shop_edit_success(){
        OwnerSignUpDTO ownerSignUpDTO = new OwnerSignUpDTO("test1@test.com", "01011111111", "tester1", "testPwd1");
        Long id = ownerService.signUp(ownerSignUpDTO);
        Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        ShopCreateDTO shopCreateDTO = ShopBuilder.shopCreateDTODummyBuild();
        Long shopId = shopService.createShop(shopCreateDTO);
        Assertions.assertThat(shopId).isNotNull();

        ShopEditDTO shopEditDTO = ShopBuilder.shopEditDTONameBiulder("가게이름수정");
        shopService.editShop(shopId, shopEditDTO);
        ShopDetails shopDetails = shopService.findShop(shopId);

        // TODO :: 왜 CreationTimestamp, UpdateTimestamp 적용 안 됨?
        System.out.println(shopDetails);
        Assertions.assertThat(shopDetails.getModifiedAt()).isNotNull();
    }

    @DisplayName("가게 삭제 성공")
    @Test
    public void shop_delete_success(){
        ShopCreateDTO shopCreateDTO = ShopBuilder.shopCreateDTODummyBuild();
        Long shopId = shopService.createShop(shopCreateDTO);
        ShopDetails shopDetails = shopService.findShop(shopId);
        shopService.deleteShop(shopDetails.getId());
    }

    @DisplayName("없는 shopId로 삭제를 요청한 경우 실패")
    @Test
    public void shop_delete_fail(){
        Long shopId = (long)10;
        ShopDetails shopDetails = shopService.findShop(shopId);
        shopService.deleteShop(shopDetails.getId());
    }
}

