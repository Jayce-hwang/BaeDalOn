package com.studyveloper.baedalon.item;

import com.studyveloper.baedalon.builder.GroupBuilder;
import com.studyveloper.baedalon.builder.ShopBuilder;
import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.group.GroupRepository;
import com.studyveloper.baedalon.shop.Shop;
import com.studyveloper.baedalon.shop.ShopRepository;
import com.studyveloper.baedalon.shop.ShopService;
import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.user.OwnerRepository;
import com.studyveloper.baedalon.user.OwnerService;
import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceImplTest {
    @Autowired ItemService itemService;
    @Autowired EntityManager entityManager;
    @Autowired OwnerService ownerService;
    @Autowired OwnerRepository ownerRepository;
    @Autowired ShopService shopService;
    @Autowired ShopRepository shopRepository;
    @Autowired GroupRepository groupRepository;

    @BeforeEach
    public void setup() {
        OwnerSignUpDTO ownerSignUpDTO = new OwnerSignUpDTO("test1@test.com",
                "010111111112",
                "tester1",
                "testPwd1");

        Long id = ownerService.signUp(ownerSignUpDTO);
        Owner owner = ownerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        OwnerSignUpDTO ownerSignUpDTO2 = new OwnerSignUpDTO("test2@test.com",
                "01011111111",
                "tester2",
                "testPwd2");

        Long id2 = ownerService.signUp(ownerSignUpDTO2);
        Owner owner2 = ownerRepository.findById(id2).orElseThrow(EntityNotFoundException::new);

        ShopCreateDTO shopCreateDTO1 = ShopBuilder.shopCreateDTOBuild("address",
                "desc",
                "name",
                "phone",
                owner.getId());

        ShopCreateDTO shopCreateDTO2 = ShopBuilder.shopCreateDTOBuild("address",
                "desc",
                "name",
                "phone",
                owner2.getId());

        Long shopId1 = shopService.createShop(shopCreateDTO1);
        Long shopId2 = shopService.createShop(shopCreateDTO2);

        Shop shop1 = shopRepository.findById(shopId1).get();
        Shop shop2 = shopRepository.findById(shopId2).get();

        Group group1 = GroupBuilder.getGroup(shop1);
        Group group2 = GroupBuilder.getGroup(shop2);

        entityManager.persist(group1);
        entityManager.persist(group2);

        group1.chagneSortOrder(1);
        group2.chagneSortOrder(1);
    }

    @Test
    @DisplayName("")
    public void testCreateItem_success_firstItem() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> gruops = groupRepository.findByShopId(shops.get(0).getId());


    }
}