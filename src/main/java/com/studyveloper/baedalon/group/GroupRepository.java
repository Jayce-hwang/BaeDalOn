package com.studyveloper.baedalon.group;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByShopId(long shopId);
    List<Group> findByShopIdOrderBySortOrderAsc(long shopId);
}
