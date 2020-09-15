package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.shop.dto.ShopDetails;
import com.studyveloper.baedalon.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.user.OwnerRepository;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.util.Pageable;
import com.studyveloper.baedalon.util.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
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
    public Long createShop(ShopCreateDTO shopCreateDTO) {
        // TODO :: 머지 후 변경
        Owner owner = ownerRepository.findById(shopCreateDTO.getOwnerId()).orElseThrow(EntityNotFoundException::new);
        Shop shop;
        try{
            ShopDetails shopDetails = searchShopByOwnerId(owner.getId());
            return null;
            // TODO :: 가게를 이미 생성한 업주인 경우 어캐? searchShopByOwner를 만들긴 했는데 여기서 catch에서 가게 생성하는게 맞는건가? catch에서 하니까 좀..
        }catch(EntityNotFoundException e){
            shop= Shop.builder()
                    .address(shopCreateDTO.getAddress())
                    .description(shopCreateDTO.getDescription())
                    .name(shopCreateDTO.getName())
                    .owner(owner)
                    .phone(shopCreateDTO.getPhone())
                    .build();
            shop = shopRepository.save(shop);
        }
        return shop.getId();
    }
    @Override
    public void openShop( Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shop.open();
    }

    @Override
    public void closeShop( Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shop.close();
    }

    @Override
    public void editShop(Long shopId, ShopEditDTO shopEditDTO) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shop.modify(shopEditDTO.getName(), shopEditDTO.getAddress(), shopEditDTO.getPhone(), shopEditDTO.getDescription());
    }

    @Override
    public void deleteShop( Long shopId) {
        // TODO :: 가게도 삭제상태로? 아니면 바로 삭제?
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shopRepository.delete(shop);
    }

    @Override
    public ShopDetails findShop(Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        ShopDetails shopDetails = ShopDetails.builder()
                .id(shop.getId())
                .address(shop.getAddress())
                .description(shop.getDescription())
                .name(shop.getName())
                .ownerId(shop.getOwner().getId())
                .phone(shop.getPhone())
                .status(shop.getStatus())
                .build();
        return shopDetails;
    }

    public ShopDetails searchShopByOwnerId(Long ownerId){
        Shop shop = shopRepository.findByOwnerId(ownerId).orElseThrow(EntityNotFoundException::new);
        ShopDetails shopDetails = ShopDetails.builder().
                    id(shop.getId()).
                    ownerId(ownerId).
                    name(shop.getName()).
                    address(shop.getAddress()).
                    status(shop.getStatus()).
                    phone(shop.getPhone()).
                    description(shop.getDescription()).
                    createdAt(shop.getCreatedAt()).
                    modifiedAt(shop.getModifiedAt()).
                    build();
        return shopDetails;
    }
    @Override
    public List<ShopDetails> searchShop(Pageable pageable, SearchCondition searchCondition) {
        //?
        return null;
    }
}
