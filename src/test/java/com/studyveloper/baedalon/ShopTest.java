package com.studyveloper.baedalon;

import com.studyveloper.baedalon.repository.OwnerRepository;
import com.studyveloper.baedalon.shop.Shop;
import com.studyveloper.baedalon.shop.ShopService;
import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.shop.dto.ShopDetails;
import com.studyveloper.baedalon.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.user.OwnerStatus;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ShopTest {
    @Autowired
    private ShopService shopService;
    @Autowired
    private OwnerRepository ownerRepository;


    //가게 성공 등록 테스트
    //DisplayName 어캐 하기로했지?
    @Test
    public void 가게생성(){
        Owner owner = Owner.builder()
                .id((long)0)
                .build();
        owner = ownerRepository.save(owner);
        ShopCreateDTO shopCreateDTO = ShopCreateDTO.builder()
                .address("가게 주소")
                .description("가게 설명")
                .name("가게 이름")
                .owner(owner)
                .phone("가게 번호")
                .status(ShopStatus.OPEN)
                .build();
        Long shopId = shopService.createShop(shopCreateDTO, owner.getId());
        Assert.isTrue(shopId==owner.getId(), "가게 생성 성공");
    }

    @Test
    public void 가게정보수정(){
        Owner owner = Owner.builder()
                .id((long)0)
                .build();
        owner = ownerRepository.save(owner);
        ShopCreateDTO shopCreateDTO = ShopCreateDTO.builder()
                .address("가게 주소")
                .description("가게 설명")
                .name("가게 이름")
                .owner(owner)
                .phone("가게 번호")
                .status(ShopStatus.OPEN)
                .build();
        Long shopId = shopService.createShop(shopCreateDTO, owner.getId());
        ShopDetails shopDetails = shopService.findShop(shopId);
        ShopEditDTO shopEditDTO = ShopEditDTO.builder()
                .id(shopId)
                .address("가게 정보 수정")
                .description("가게 설명수정")
                .name("가게 이름 수정")
                .owner(owner)
                .phone("가게 번호 수정")
                .status(ShopStatus.CLOSE)
                .build();
        shopService.editShop(shopId, shopEditDTO);
        ShopDetails shopDetails1 = shopService.findShop(shopId);
        Assert.isTrue(!shopDetails.equals(shopDetails1), "수정 성공!");
    }

    @Test
    public void 가게삭제(){
        Owner owner = Owner.builder()
                .id((long)0)
                .build();
        owner = ownerRepository.save(owner);
        ShopCreateDTO shopCreateDTO = ShopCreateDTO.builder()
                .address("가게 주소")
                .description("가게 설명")
                .name("가게 이름")
                .owner(owner)
                .phone("가게 번호")
                .status(ShopStatus.OPEN)
                .build();
        Long shopId = shopService.createShop(shopCreateDTO, owner.getId());
        ShopDetails shopDetails = shopService.findShop(shopId);
        shopService.deleteShop(owner.getId(), shopDetails.getId());
        try{
            shopService.findShop(shopDetails.getId());
            Assert.isNull(null, "삭제실패");
        }catch(EntityNotFoundException e){
            Assert.notNull(null, "삭제완료");
        }
    }
}

