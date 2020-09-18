package com.studyveloper.baedalon.module.shop;

import com.studyveloper.baedalon.builder.ShopBuilder;
import com.studyveloper.baedalon.module.shop.service.ShopService;
import com.studyveloper.baedalon.module.user.dao.OwnerRepository;
import com.studyveloper.baedalon.module.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.module.shop.dto.ShopDetails;
import com.studyveloper.baedalon.module.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.module.user.domain.Owner;
import com.studyveloper.baedalon.module.user.service.OwnerService;
import com.studyveloper.baedalon.module.user.dto.OwnerSignUpDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 가게생성 성공case
     * 1.업주 임의 생성
     * 2.업주 임의 생성 검증
     * 3.ShopBuilder.shopCreateDTODummyBuild() 사용해서 ShopCreateDTO dummy생성
     * 4.가게 생성 검증
     */
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

    /**
     * 가게생성 실패case
     * 1.업주 임의 생성
     * 2.업주 임의 생성 검증
     * 3.ShopBuilder.shopCreateDTODummyBuild() 사용해서 ShopCreateDTO dummy생성
     * 4.가게 생성 검증
     *
     */
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

    /**
     * 가게정보 수정 성공case
     * 1. 업주 임의 생성
     * 2. 업주 임의 생성 검증
     * 3. ShopBuilder.shopCreateDTODummyBuild() 사용해서 ShopCreateDTO dummy생성
     * 4. 생성한 가게 검증
     * 5. ShopBuilder.shopEditDTONameBuilder() 사용해서 ShopEditDTO 생성
     * 6. 수정 검증
     * 실패하는 경우는 뭐가 있지?
     */
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

    /**
     * 가게정보 수정case
     * 1. 업주 임의 생성
     * 2. 업주 임의 생성 검증
     * 3. ShopBuilder.shopCreateDTODummyBuild() 사용해서 ShopCreateDTO dummy생성
     * 4. 생성한 가게 검증
     * 5. 가게 삭제
     * 6. try,catch삭제 검증
     */
    @DisplayName("가게 삭제 성공")
    @Test
    public void shop_delete_success(){
        OwnerSignUpDTO ownerSignUpDTO = new OwnerSignUpDTO("test1@test.com", "01011111111", "tester1", "testPwd1");
        Long id = ownerService.signUp(ownerSignUpDTO);
        Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        ShopCreateDTO shopCreateDTO = ShopBuilder.shopCreateDTODummyBuild();
        Long shopId = shopService.createShop(shopCreateDTO);
        Assertions.assertThat(shopId).isNotNull();

        ShopDetails shopDetails = shopService.findShop(shopId);
        shopService.deleteShop(shopDetails.getId());

        try{
            shopDetails = shopService.findShop(shopId);
        }catch(EntityNotFoundException e){
            Assertions.assertThat(e).isNotNull();
        }
    }

    /**
     * 가게정보 수정case
     * 1. 임의 shopId로 삭제 요청
     * 2. try{}catch{}검증
     */
    @DisplayName("없는 shopId로 삭제를 요청한 경우 실패")
    @Test
    public void shop_delete_fail(){
        Long shopId = (long)10;
        try{
            shopService.deleteShop(shopId);
        }catch(EntityNotFoundException e){
            Assertions.assertThat(e).isNotNull();
        }

    }
}

