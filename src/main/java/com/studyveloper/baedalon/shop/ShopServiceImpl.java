package com.studyveloper.baedalon.service;

import com.studyveloper.baedalon.dto.ShopCreateDTO;
import com.studyveloper.baedalon.dto.ShopDetails;
import com.studyveloper.baedalon.dto.ShopEditDTO;
import com.studyveloper.baedalon.repository.OwnerRepository;
import com.studyveloper.baedalon.repository.ShopRepository;
import com.studyveloper.baedalon.shop.Shop;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.util.Pageable;
import com.studyveloper.baedalon.util.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ShopServiceImpl implements ShopService {
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Override
    public Long createShop(ShopCreateDTO shopCreateDTO, Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).get();
        Shop shop = new Shop(shopCreateDTO.getId(), shopCreateDTO.getOwner(), shopCreateDTO.getName(), shopCreateDTO.getAddress(),
                shopCreateDTO.getPhone(), shopCreateDTO.getStatus(), shopCreateDTO.getDescription(), shopCreateDTO.getCreatedAt(), shopCreateDTO.getModifiedAt());
        shop = shopRepository.save(shop);
        return shop.getId();
    }
    @Override
    public void openShop(Long ownerId, Long shopId) {
        Shop shop = shopRepository.findById(shopId).get();
        if(shop.getOwner().getId() == ownerId){
            shop.open();
            shopRepository.save(shop);
        }else{
            //throws
        }
    }

    @Override
    public void closeShop(Long ownerId, Long shopId) {
        Shop shop = shopRepository.findById(shopId).get();
        if(shop.getOwner().getId() == ownerId){
            shop.close();
            shopRepository.save(shop);
        }else{
            //throws
        }
    }

    @Override
    public void editShop(Long shopId, ShopEditDTO shopEditDTO) {
        Shop shop = shopRepository.findById(shopId).get();
        shop.modify(shopEditDTO.getName(), shopEditDTO.getAddress(), shopEditDTO.getPhone(), shopEditDTO.getDescription());
        shopRepository.save(shop);
    }

    @Override
    public void deleteShop(Long ownerId, Long shopId) {
        Shop shop = shopRepository.findById(shopId).get();
        if(shop.getOwner().getId() == ownerId){
            shopRepository.delete(shop);
        }else{
            //throws
        }
    }

    @Override
    public ShopDetails findShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId).get();
        ShopDetails shopDetails = new ShopDetails(shop.getId(), shop.getOwner(), shop.getName(), shop.getAddress(),
                shop.getPhone(), shop.getStatus(), shop.getDescription(), shop.getCreatedAt(), shop.getModifiedAt());
        return shopDetails;
    }

    @Override
    public List<ShopDetails> searchShop(Pageable pageable, SearchCondition searchCondition) {
        //?
        return null;
    }
}
