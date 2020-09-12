package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.dto.GroupCreateDto;
import com.studyveloper.baedalon.dto.GroupEditDto;
import com.studyveloper.baedalon.repository.GroupRepository;
import com.studyveloper.baedalon.service.GroupService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupTest {
    @Autowired
    GroupService groupService;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ShopRepository shopRepository;
    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Shop shop1 = new Shop();
        Shop shop2 = new Shop();

        shopRepository.save(shop1);
        shopRepository.save(shop2);

        System.out.println("ShopId = " + shop1.getId());

        Group group1 = GroupTestFactory.getGroup(shop1);
        Group group2 = GroupTestFactory.getGroup(shop1);
        Group group3 = GroupTestFactory.getGroup(shop2);
        Group group4 = GroupTestFactory.getGroup(shop2);

        entityManager.persist(group1);
        entityManager.persist(group2);
        entityManager.persist(group3);
        entityManager.persist(group4);

        group1.chagneSortOrder(1);
        group2.chagneSortOrder(2);
        group3.chagneSortOrder(1);
        group4.chagneSortOrder(2);
    }

    @Test
    @DisplayName("createGroup 성공 테스트 (첫번째 값 추가시)")
    public void testCreateGroup_success_firstGroupCreate() {
        Shop shop = new Shop();
        shopRepository.save(shop);

        GroupCreateDto groupCreateDto = GroupTestFactory.getGroupCreateDto();

        long id = groupService.createGroup(groupCreateDto, shop.getId());

        Group group = groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        assertThat(group)
                .hasFieldOrPropertyWithValue("sortOrder", (long)1);
    }

    @Test
    @DisplayName("createGroup 성공 테스트 (n번째 값 추가시)")
    public void testCreateGroup_success_multipleGroupCreate() {
        GroupCreateDto groupCreateDto = GroupTestFactory.getGroupCreateDto();

        long id = groupService.createGroup(groupCreateDto, (long)1);

        Group group = groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        assertThat(group)
                .hasFieldOrPropertyWithValue("sortOrder", (long)3);
    }

    @Test
    @DisplayName("editGroup 성공 테스트")
    public void testEditGroup_success() {
        //TODO:: 테스트 방식 점검 필요
        List<Group> groups = groupRepository.findAll();
        Group testGroup = groups.get(0);

        GroupEditDto groupEditDto = GroupTestFactory.getGroupEditDto();
        groupService.editGroup(testGroup.getId(), groupEditDto);

        Group group = groupRepository.findById(testGroup.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertThat(group)
                .hasFieldOrPropertyWithValue("name", groupEditDto.getName())
                .hasFieldOrPropertyWithValue("description", groupEditDto.getDescription())
                .hasFieldOrPropertyWithValue("status", groupEditDto.getStatus());
    }

    @Test
    @DisplayName("swapGroupOrder 성공 테스트")
    public void testSwapGroupOrder_success() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());

        Group group = groups.get(0);
        Group targetGroup = groups.get(1);

        long originGroupSortOrder = group.getSortOrder();
        long targetGroupSortOrder = targetGroup.getSortOrder();

        groupService.swapGroupOrder(group.getId(), targetGroup.getId());

        assertThat(group)
                .hasFieldOrPropertyWithValue("sortOrder", targetGroupSortOrder);

        assertThat(targetGroup)
                .hasFieldOrPropertyWithValue("sortOrder", originGroupSortOrder);
    }
}