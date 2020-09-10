package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.dto.GroupCreateDto;
import com.studyveloper.baedalon.repository.GroupRepository;
import com.studyveloper.baedalon.service.GroupService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupTest {
    @Autowired
    GroupService groupService;
    @Autowired
    GroupRepository groupRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("createGroup 성공 테스트")
    public void testCreateGroup_success_firstGroupCreate() {
        // TODO: Shop팩토리를 통해 shop 객체를 받아오는 작업으로 전환 필요
        Shop shop = new Shop();
        entityManager.persist(shop);

        entityManager.flush();
        entityManager.clear();

        GroupCreateDto groupCreateDto = GroupTestFactory.getGroupCreateDto();

        long id = groupService.createGroup(groupCreateDto, shop.getId());

        Group group = groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        assertThat(group)
                .hasFieldOrPropertyWithValue("sortOrder", (long)1);
    }
}