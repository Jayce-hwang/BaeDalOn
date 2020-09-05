package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.shop.dto.ShopDetails;
import com.studyveloper.baedalon.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.util.Pageable;
import com.studyveloper.baedalon.util.SearchCondition;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ShopService {
    public Long createShop(ShopCreateDTO shopCreateDTO, Long ownerId);
    public void openShop(Long ownerId, Long shopId);
    public void closeShop(Long ownerId, Long shopId);
    public void editShop(Long shopId, ShopEditDTO shopEditDTO);
    public void deleteShop(Long ownerId, Long shopId);
    public ShopDetails findShop(Long shopId);
    public List<ShopDetails> searchShop(Pageable pageable, SearchCondition searchCondition);
}
