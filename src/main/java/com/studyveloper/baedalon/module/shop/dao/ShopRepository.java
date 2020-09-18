package com.studyveloper.baedalon.module.shop.dao;

import com.studyveloper.baedalon.module.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByOwnerId(Long ownerId);
}
