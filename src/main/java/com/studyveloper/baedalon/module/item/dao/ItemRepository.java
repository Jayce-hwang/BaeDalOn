package com.studyveloper.baedalon.module.item.dao;

import com.studyveloper.baedalon.module.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositorySearch {
    List<Item> findByShopIdAndGroupIdOrderBySortOrderAsc(Long shopId, Long groupId);
    List<Item> findByGroupId(Long groupId);
}
