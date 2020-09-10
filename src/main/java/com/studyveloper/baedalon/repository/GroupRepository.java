package com.studyveloper.baedalon.repository;

import com.studyveloper.baedalon.shop.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
    List<Group> findByShopId(long shopId);
    List<Group> findByShopIdOrderBySortOrderDesc(long shopId);
}
