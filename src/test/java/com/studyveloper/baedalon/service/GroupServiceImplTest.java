package com.studyveloper.baedalon.service;

import com.studyveloper.baedalon.dto.GroupCreateDto;
import com.studyveloper.baedalon.dto.GroupDetails;
import com.studyveloper.baedalon.dto.GroupEditDto;
import com.studyveloper.baedalon.repository.GroupRepository;
import com.studyveloper.baedalon.shop.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
import com.studyveloper.baedalon.shop.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceImplTest {
    @Autowired GroupService groupService;
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
}