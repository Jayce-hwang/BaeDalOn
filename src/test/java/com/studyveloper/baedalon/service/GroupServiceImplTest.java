package com.studyveloper.baedalon.service;

import com.studyveloper.baedalon.group.GroupService;
import com.studyveloper.baedalon.group.dto.GroupCreateDto;
import com.studyveloper.baedalon.group.dto.GroupDetails;
import com.studyveloper.baedalon.group.dto.GroupEditDto;
import com.studyveloper.baedalon.group.GroupRepository;
import com.studyveloper.baedalon.shop.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
import com.studyveloper.baedalon.shop.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupServiceImplTest {
    @Autowired
    GroupService groupService;
    @Autowired GroupRepository groupRepository;
    @PersistenceContext EntityManager entityManager;

    @Test
    public void basicCRUDTest() {
        GroupCreateDto groupCreateDto = new GroupCreateDto();
        groupCreateDto.setDescription("desc");
        groupCreateDto.setName("name");

        Shop shop = new Shop();
        entityManager.persist(shop);

        long shopId = shop.getId();

        entityManager.flush();
        entityManager.clear();

        long createdGroupId = groupService.createGroup(groupCreateDto, shopId);

        GroupDetails groupDetails = groupService.findGroup(createdGroupId);

        assertThat(groupCreateDto.getDescription()).isEqualTo(groupDetails.getDescription());
        assertThat(groupCreateDto.getName()).isEqualTo(groupDetails.getName());

        GroupEditDto groupEditDto = new GroupEditDto();
        groupEditDto.setDescription("desced");
        groupEditDto.setName("nameed");
        groupEditDto.setStatus(GroupStatus.HIDDEN);

        groupService.editGroup(createdGroupId, groupEditDto);

        GroupDetails editedGroupDetails = groupService.findGroup(createdGroupId);

        assertThat(editedGroupDetails.getDescription()).isEqualTo("desced");
        assertThat(editedGroupDetails.getName()).isEqualTo("nameed");
        assertThat(editedGroupDetails.getStatus()).isEqualByComparingTo(GroupStatus.HIDDEN);

        groupService.deleteGroup(createdGroupId);

        long count = groupRepository.count();

        assertThat(count).isEqualTo(0);
    }

    @Test
    public void findGroupsTest() {
        GroupCreateDto groupCreateDto = new GroupCreateDto();
        groupCreateDto.setDescription("desc");
        groupCreateDto.setName("name");

        GroupCreateDto groupCreateDto2 = new GroupCreateDto();
        groupCreateDto2.setDescription("desc");
        groupCreateDto2.setName("name");

        Shop shop = new Shop();
        entityManager.persist(shop);

        long shopId = shop.getId();

        groupService.createGroup(groupCreateDto, shopId);
        groupService.createGroup(groupCreateDto2, shopId);

        List<GroupDetails> groupDetailsList = groupService.findGroups(shopId);

        assertThat(groupDetailsList.size()).isEqualTo(2);
    }

    @Test
    public void swapSortOrderTest() {
        GroupCreateDto groupCreateDto = new GroupCreateDto();
        groupCreateDto.setDescription("desc");
        groupCreateDto.setName("name");

        GroupCreateDto groupCreateDto2 = new GroupCreateDto();
        groupCreateDto2.setDescription("desc");
        groupCreateDto2.setName("name");

        Shop shop = new Shop();
        entityManager.persist(shop);

        long shopId = shop.getId();

        Long groupId = groupService.createGroup(groupCreateDto, shopId);
        Long targetGroupId = groupService.createGroup(groupCreateDto2, shopId);

        Group group = entityManager.find(Group.class, groupId);
        Group targetGroup =entityManager.find(Group.class, targetGroupId);

        Long sortOrder = group.getSortOrder();
        Long targetSortOrder = targetGroup.getSortOrder();

        groupService.swapGroupOrder(groupId, targetGroupId);

        assertThat(sortOrder).isEqualTo(targetGroup.getSortOrder());
        assertThat(targetSortOrder).isEqualTo(group.getSortOrder());
    }
}