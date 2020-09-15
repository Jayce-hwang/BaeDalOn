package com.studyveloper.baedalon.group;

import com.studyveloper.baedalon.builder.GroupBuilder;
import com.studyveloper.baedalon.builder.ShopBuilder;
import com.studyveloper.baedalon.group.dto.GroupCreateDto;
import com.studyveloper.baedalon.group.dto.GroupDetails;
import com.studyveloper.baedalon.group.dto.GroupEditDto;
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
import org.springframework.dao.EmptyResultDataAccessException;
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
    ShopService shopService;
    @Autowired
    OwnerService ownerService;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ShopRepository shopRepository;
    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
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
        Group group2 = GroupBuilder.getGroup(shop1);
        Group group3 = GroupBuilder.getGroup(shop2);
        Group group4 = GroupBuilder.getGroup(shop2);

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
        OwnerSignUpDTO ownerSignUpDTO = new OwnerSignUpDTO("test3@test.com",
                "0101111444",
                "tester3",
                "testPwd3");

        Long ownerId = ownerService.signUp(ownerSignUpDTO);

        ShopCreateDTO shopCreateDTO = ShopBuilder.shopCreateDTOBuild("address",
                "desc",
                "name",
                "phone",
                ownerId);

        Long shopId = shopService.createShop(shopCreateDTO);
        Shop shop = shopRepository.findById(shopId).get();

        GroupCreateDto groupCreateDto = GroupBuilder.getGroupCreateDto();
        Long id = groupService.createGroup(groupCreateDto, shop.getId());
        Group group = groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        assertThat(group)
                .hasFieldOrPropertyWithValue("sortOrder", (long)1);
    }

    @Test
    @DisplayName("createGroup 성공 테스트 (n번째 값 추가시)")
    public void testCreateGroup_success_multipleGroupCreate() {
        List<Shop> shops = shopRepository.findAll();
        Shop shop = shops.get(0);

        GroupCreateDto groupCreateDto = GroupBuilder.getGroupCreateDto();

        long id = groupService.createGroup(groupCreateDto, shop.getId());

        Group group = groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        assertThat(group)
                .hasFieldOrPropertyWithValue("sortOrder", (long)3);
    }

    @Test
    @DisplayName("createGroup 실패 테스트 파라미터에 null값이 존재할 경우")
    public void testCreateGroup_fail_nullParameter() {
        List<Shop> shops = shopRepository.findAll();

        assertThatThrownBy(() -> {
            groupService.createGroup(null, shops.get(0).getId()); })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("createGroup 실패 테스트 존재하지 않는 shopId를 파라미터로 제공할 경우")
    public void testCreateGroup_fail_not_exist_shop() {
        GroupCreateDto groupCreateDto = GroupBuilder.getGroupCreateDto();

        assertThatThrownBy(() -> {
            groupService.createGroup(groupCreateDto, (long)-1); })
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("editGroup 성공 테스트")
    public void testEditGroup_success() {
        //TODO:: 테스트 방식 점검 필요
        List<Group> groups = groupRepository.findAll();
        Group testGroup = groups.get(0);

        GroupEditDto groupEditDto = GroupBuilder.getGroupEditDto();
        groupService.editGroup(testGroup.getId(), groupEditDto);

        Group group = groupRepository.findById(testGroup.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertThat(group)
                .hasFieldOrPropertyWithValue("name", groupEditDto.getName())
                .hasFieldOrPropertyWithValue("description", groupEditDto.getDescription())
                .hasFieldOrPropertyWithValue("status", groupEditDto.getStatus());
    }

    @Test
    @DisplayName("editGrop 실패 테스트 파라미터에 null값을 제공할 경우")
    public void testEdiGroup_fail_nullParameter() {
        List<Group> groups = groupRepository.findAll();

        assertThatThrownBy(() -> {
            groupService.editGroup(groups.get(0).getId(), null); })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("editGroup 실패 테스트 존재하지 않는 Group의 id를 파라미터로 제공할 경우")
    public void testEdiGroup_fail_not_exist_group() {
        GroupEditDto groupEditDto = GroupBuilder.getGroupEditDto();

        assertThatThrownBy(() -> {
            groupService.editGroup((long)-1, groupEditDto); })
                .isInstanceOf(EntityNotFoundException.class);
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

    @Test
    @DisplayName("swapGroupOrder 실패 테스트 서로 다른 Shop에 속한 Group일 경우")
    public void testSwapGroupOrder_fail_belong_different_shop() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());
        List<Group> targetGroups = groupRepository.findByShopId(shops.get(1).getId());

        Group group = groups.get(0);
        Group targetGroup = targetGroups.get(0);

        assertThatThrownBy(() -> {
            groupService.swapGroupOrder(group.getId(), targetGroup.getId()); })
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("swapGroupOrder 실패 테스트 파라미터에 null값이 있을 경우")
    public void testSwapGroupOrder_fail_nullParameter() {
        assertThatThrownBy(() -> {
            groupService.swapGroupOrder(null, null); })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("swapGroupOrder 실패 테스트 존재하지 않는 Group의 id를 제공할 경우")
    public void testSwapGroupOrder_fail_not_exist_Group() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());
        Group group = groups.get(0);

        assertThatThrownBy(() -> {
            groupService.swapGroupOrder(group.getId(), (long)-1); })
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("deleteGroup 성공 테스트")
    public void testDeleteGroup_success() {
        List<Group> groups = groupRepository.findAll();

        groupService.deleteGroup(groups.get(0).getId());
        groupService.deleteGroup(groups.get(1).getId());

        List<Group> result = groupRepository.findAll();

        assertThat(result.size())
                .isEqualTo(groups.size() - 2);
    }

    @Test
    @DisplayName("deleteGroup 실패 테스트 존재하지 않는 group을 제공할 경우")
    public void testDeleteGroup_fail_not_exist_group() {
        assertThatThrownBy(() -> {
            groupService.deleteGroup((long)-1); })
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("deleteGroup 실패 테스트 파라미터에 null값이 있을 경우")
    public void testDeleteGroup_fail_nullParameter() {
        assertThatThrownBy(() -> {
            groupService.deleteGroup(null); })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("findGroup 성공 테스트")
    public void testFindGroup_success() {
        List<Shop> shops = shopRepository.findAll();

        Group group = GroupBuilder.getGroup(shops.get(0));
        groupRepository.save(group);

        GroupDetails findGroup = groupService.findGroup(group.getId());

        assertThat(findGroup)
                .hasFieldOrPropertyWithValue("id", group.getId())
                .hasFieldOrPropertyWithValue("name", group.getName())
                .hasFieldOrPropertyWithValue("description", group.getDescription())
                .hasFieldOrPropertyWithValue("sortOrder", group.getSortOrder())
                .hasFieldOrPropertyWithValue("status", group.getStatus());
    }

    @Test
    @DisplayName("findGroup 실패 테스트 파라미터에 null값이 존재할 경우")
    public void testFindGroup_fail_nullParameter() {
        assertThatThrownBy(() -> {
            groupService.findGroup(null); })
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("findGroup 실패 테스트 존재하지 않는 Group을 제공할 경우")
    public void testFindGroup_fail_not_exist_Group() {
        assertThatThrownBy(() -> {
            groupService.findGroup((long)-1); })
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("findGroups 성공 테스트")
    public void testFindGroups_success() {
        List<Shop> shops = shopRepository.findAll();
        List<Group> groups = groupRepository.findByShopId(shops.get(0).getId());

        List<GroupDetails> groupDetails = groupService.findGroups(shops.get(0).getId());

        assertThat(groupDetails.size())
                .isEqualTo(groups.size());
    }

    @Test
    @DisplayName("findGroups 성공 테스트 shop이 존재하지 않을경우")
    public void testFindGroups_success_not_exist_shop() {
        List<GroupDetails> groupDetails = groupService.findGroups((long)-1);

        assertThat(groupDetails.size())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("findGroups 실패 테스트 파라미터에 null을 제공할 경우")
    public void testFindGroups_fail_not_exist_Shop() {
        assertThatThrownBy(() -> {
            groupService.findGroup(null); })
                .isInstanceOf(IllegalArgumentException.class);
    }
}