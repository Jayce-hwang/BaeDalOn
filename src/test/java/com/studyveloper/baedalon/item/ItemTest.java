package com.studyveloper.baedalon.item;

import com.studyveloper.baedalon.builder.GroupBuilder;
import com.studyveloper.baedalon.builder.ItemBuilder;
import com.studyveloper.baedalon.builder.ShopBuilder;
import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.group.GroupRepository;
import com.studyveloper.baedalon.item.dto.ItemCreateDto;
import com.studyveloper.baedalon.item.dto.ItemEditDto;
import com.studyveloper.baedalon.shop.Shop;
import com.studyveloper.baedalon.shop.ShopRepository;
import com.studyveloper.baedalon.shop.ShopService;
import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.user.OwnerRepository;
import com.studyveloper.baedalon.user.OwnerService;
import com.studyveloper.baedalon.user.dto.OwnerSignUpDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemTest {
    @Autowired EntityManager entityManager;
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
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

        Item item1 = ItemBuilder.getItem(shop1, group1);
        Item item2 = ItemBuilder.getItem(shop1, group1);
        Item item3 = ItemBuilder.getItem(shop2, group2);
        Item item4 = ItemBuilder.getItem(shop2, group2);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
    }

    @Test
    @DisplayName("createItem 성공 테스트 (첫번째 값 추가시)")
    public void testCreateItem_success_firstItem() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());
        List<Item> items
                = itemRepository.findByShopIdAndGroupIdOrderBySortOrderAsc(shops.get(0).getId(), groups.get(0).getId());

        ItemCreateDto itemCreateDto = ItemBuilder.getItemCreateDto(shops.get(0), groups.get(0));

        Long id = itemService.createItem(itemCreateDto);

        Item item = itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        assertThat(item)
                .hasFieldOrPropertyWithValue("name", itemCreateDto.getName())
                .hasFieldOrPropertyWithValue("price", itemCreateDto.getPrice())
                .hasFieldOrPropertyWithValue("description", itemCreateDto.getDescription())
                .hasFieldOrPropertyWithValue("sortOrder", items.get(items.size() -1).getSortOrder() + 1);
    }

    @Test
    @DisplayName("editItem 성공 테스트")
    public void testEditItem_success() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());
        List<Item> items
                = itemRepository.findByShopIdAndGroupIdOrderBySortOrderAsc(shops.get(0).getId(), groups.get(0).getId());

        ItemEditDto itemEditDto = ItemBuilder.getItemEditDto();

        itemService.editItem(items.get(0).getId(), itemEditDto);

        Item item = itemRepository.findById(items.get(0).getId()).orElseThrow(EntityNotFoundException::new);

        assertThat(item)
                .hasFieldOrPropertyWithValue("name", itemEditDto.getName())
                .hasFieldOrPropertyWithValue("description", itemEditDto.getDescription())
                .hasFieldOrPropertyWithValue("price", itemEditDto.getPrice());
    }

    @Test
    @DisplayName("swapItem 성공 테스트")
    public void testSwapItem_success() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());
        List<Item> items
                = itemRepository.findByShopIdAndGroupIdOrderBySortOrderAsc(shops.get(0).getId(), groups.get(0).getId());

        Item item = items.get(0);
        Item targetItem = items.get(1);

        long sortOrder = item.getSortOrder();
        long targetSortOrder = targetItem.getSortOrder();

        itemService.swapItem(item.getId(), targetItem.getId());

        item = itemRepository.findById(item.getId()).orElseThrow(EntityNotFoundException::new);
        targetItem = itemRepository.findById(targetItem.getId()).orElseThrow(EntityNotFoundException::new);

        assertThat(item.getSortOrder()).isEqualTo(targetSortOrder);
        assertThat(targetItem.getSortOrder()).isEqualTo(sortOrder);
    }

    @Test
    @DisplayName("hideItem and showItem 성공 테스트")
    public void testHideAndShowItem_success() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());
        List<Item> items
                = itemRepository.findByShopIdAndGroupIdOrderBySortOrderAsc(shops.get(0).getId(), groups.get(0).getId());

        Item item = items.get(0);

        itemService.hideItem(item.getId());
        item = itemRepository.findById(item.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(item.getStatus()).isEqualTo(ItemStatus.SOLD_OUT);

        itemService.showItem(item.getId());
        item = itemRepository.findById(item.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(item.getStatus()).isEqualTo(ItemStatus.ON_SALE);
    }

    @Test
    @DisplayName("represent and unrepresent 성공 테스트")
    public void representAndUnrepresent_success() {

    }
}