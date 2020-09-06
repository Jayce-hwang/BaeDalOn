package com.studyveloper.baedalon.repository;

import com.studyveloper.baedalon.shop.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
import com.studyveloper.baedalon.shop.Shop;
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
        group.changeDescription("desc");
        group.changeGroupStatus(GroupStatus.SHOWN);

        //long count = groupRepository.count();

        //group.chagneSortOrder(count);

        groupRepository.save(group);

        System.out.println("COUNT = " + group.getSortOrder());

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

        Shop shop = new Shop();
        entityManager.persist(shop);

        long shopId = shop.getId();

        group.setShop(shop);

        groupRepository.save(group);

        List<Group> groups2 = groupRepository.findByShopId(shopId);

        assertThat(groups2.size()).isEqualTo(1);
        assertThat(groups2.get(0).getShop().getId()).isEqualTo(shopId);
    }

    @Test
    public void findGroupCountByShopId() {
        Group group = new Group();
        group.changeName("groupA");
        group.chagneSortOrder(1);
        group.changeDescription("desc");
        group.changeGroupStatus(GroupStatus.SHOWN);

        Group group2 = new Group();
        group2.changeName("groupA");
        group2.chagneSortOrder(1);
        group2.changeDescription("desc");
        group2.changeGroupStatus(GroupStatus.SHOWN);

        Shop shop = new Shop();
        Shop shop2 = new Shop();

        entityManager.persist(shop);
        entityManager.persist(shop2);

        long shopId = shop.getId();

        group.setShop(shop);
        group2.setShop(shop2);

        entityManager.persist(group);
        entityManager.persist(group2);

        entityManager.flush();
        entityManager.clear();

        /*long count = entityManager.createQuery(
                "SELECT COUNT(g) FROM Group g WHERE g.shop.id = :shopId GROUP BY g.shop.id", Long.class)
                .setParameter("shopId", shopId)
                .getSingleResult();*/

        Long count = groupRepository.findGroupCountByShopId(shopId);

        System.out.println("count = " + count);
    }
}