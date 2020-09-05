package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    public Shop findByOwnerId(Long ownerId);
}
