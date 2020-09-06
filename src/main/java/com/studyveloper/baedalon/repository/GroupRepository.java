package com.studyveloper.baedalon.repository;

import com.studyveloper.baedalon.shop.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);

    @Query("SELECT g FROM Group g WHERE g.shop.id = :shopId")
    List<Group> findByShopId(@Param("shopId") long shopId);

    @Query("SELECT COUNT(g) FROM Group g WHERE g.shop.id = :shopId GROUP BY g.shop.id")
    Long findGroupCountByShopId(@Param("shopId") long shopId);
}
