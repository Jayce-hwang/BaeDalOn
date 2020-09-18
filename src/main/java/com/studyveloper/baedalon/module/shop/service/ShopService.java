package com.studyveloper.baedalon.module.shop.service;

import com.studyveloper.baedalon.module.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.module.shop.dto.ShopDetails;
import com.studyveloper.baedalon.module.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.infra.util.Pageable;
import com.studyveloper.baedalon.infra.util.SearchCondition;

import java.util.List;


public interface ShopService {
    public Long createShop(ShopCreateDTO shopCreateDTO);
    public void openShop(Long shopId);
    public void closeShop(Long shopId);
    public void editShop(Long shopId, ShopEditDTO shopEditDTO);
    public void deleteShop(Long shopId);
    public ShopDetails findShop(Long shopId);
    public List<ShopDetails> searchShop(Pageable pageable, SearchCondition searchCondition);
}
