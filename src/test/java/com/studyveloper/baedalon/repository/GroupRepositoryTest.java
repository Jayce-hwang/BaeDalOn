package com.studyveloper.baedalon.repository;

import com.studyveloper.baedalon.shop.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupRepositoryTest {
    @Autowired GroupRepository groupRepository;
    @PersistenceContext EntityManager entityManager;

    @Test
    public void baiscCRUD() {
        Group group = new Group();
        group.changeName("groupA");
        group.chagneSortOrder(1);
        group.changeDescription("desc");
        group.changeGroupStatus(GroupStatus.SHOWN);

        groupRepository.save(group);

        Group findGroup = groupRepository.findById(group.getId()).get();

        assertThat(findGroup).isEqualTo(group);

        group.changeName("groupB");

        entityManager.flush();
        entityManager.clear();

        Group findByNameGroup = groupRepository.findByName("groupB").get();

        assertThat(findByNameGroup.getName()).isEqualTo("groupB");

        groupRepository.delete(group);

        long deletedCount = groupRepository.count();

        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByShopId() {
        Group group = new Group();
        group.changeName("groupA");
        group.chagneSortOrder(1);
        group.changeDescription("desc");
        group.changeGroupStatus(GroupStatus.SHOWN);


    }
}