package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.shop.dto.ShopDetails;
import com.studyveloper.baedalon.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.repository.OwnerRepository;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.util.Pageable;
import com.studyveloper.baedalon.util.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public Long createShop(ShopCreateDTO shopCreateDTO, Long ownerId) {
//        Owner owner = ownerRepository.findById(ownerId).orElseThrow(EntityNotFoundException::new);
        Shop shop= Shop.builder()
                .address(shopCreateDTO.getAddress())
                .description(shopCreateDTO.getDescription())
                .name(shopCreateDTO.getName())
                .owner(shopCreateDTO.getOwner())
                .phone(shopCreateDTO.getPhone())
                .status(shopCreateDTO.getStatus())
                .build();
        shop = shopRepository.save(shop);
        return shop.getId();
    }
    @Override
    public void openShop(Long ownerId, Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        if(shop.getOwner().getId() == ownerId){
            shop.open();
        }else{
            //throws
        }
    }

    @Override
    public void closeShop(Long ownerId, Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        if(shop.getOwner().getId() == ownerId){
            shop.close();
        }else{
            //throws
        }
    }

    @Override
    public void editShop(Long shopId, ShopEditDTO shopEditDTO) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shop.modify(shopEditDTO.getName(), shopEditDTO.getAddress(), shopEditDTO.getPhone(), shopEditDTO.getDescription());
    }

    @Override
    public void deleteShop(Long ownerId, Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        if(shop.getOwner().getId() == ownerId){
            shopRepository.delete(shop);
        }else{
            //throws
        }
    }

    @Override
    public ShopDetails findShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        ShopDetails shopDetails = ShopDetails.builder()
                .id(shop.getId())
                .address(shop.getAddress())
                .description(shop.getDescription())
                .name(shop.getName())
                .owner(shop.getOwner())
                .phone(shop.getPhone())
                .status(shop.getStatus())
                .build();
        return shopDetails;
    }

    @Override
    public List<ShopDetails> searchShop(Pageable pageable, SearchCondition searchCondition) {
        //?
        return null;
    }
}
