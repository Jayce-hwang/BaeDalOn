package com.studyveloper.baedalon.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByShopIdAndGroupIdOrderBySortOrderAsc(Long shopId, Long groupId);
    List<Item> findByGroupId(Long groupId);
}
