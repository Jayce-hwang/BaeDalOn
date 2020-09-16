package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.shop.dto.ShopCreateDTO;
import com.studyveloper.baedalon.shop.dto.ShopDetails;
import com.studyveloper.baedalon.shop.dto.ShopEditDTO;
import com.studyveloper.baedalon.user.OwnerRepository;
import com.studyveloper.baedalon.user.Owner;
import com.studyveloper.baedalon.util.Pageable;
import com.studyveloper.baedalon.util.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final OwnerRepository ownerRepository;

    /**
     * 가게 생성
     * @param shopCreateDTO 생성하려는 가게 정보
     * @return Long 생성된 아이디
     */
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

    /**
     * 가게상태를 open으로 변경
     * @param shopId open으로 변경하려는 가게의 Id
     */
    @Override
    public void openShop( Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shop.open();
    }

    /**
     * 가게상태를 closed로 변경
     * @param shopId closed로 변경하려는 가게의 Id
     */
    @Override
    public void closeShop( Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shop.close();
    }

    /**
     * 가게 정보 수정
     * @param shopId 수정하려는 가게의 Id
     * @param shopEditDTO 수정하려는 가게의 수정정보
     */
    @Override
    public void editShop(Long shopId, ShopEditDTO shopEditDTO) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shop.modify(shopEditDTO.getName(), shopEditDTO.getAddress(), shopEditDTO.getPhone(), shopEditDTO.getDescription());
    }

    /**
     * 가게 삭제
     * @param shopId 삭제하려는 가게의 Id
     */
    @Override
    public void deleteShop( Long shopId) {
        // TODO :: 가게도 삭제상태로? 아니면 바로 삭제?
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);
        shopRepository.delete(shop);
    }

    /**
     * 가게Id와 일치하는 가게 1개 찾기
     * @param shopId 찾으려는 가게의 Id
     * @return ShopDetals 찾은 가게의 상세정보
     */
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

    /**
     * 업주 Id로 가게찾기
     * @param ownerId  가게 정보를 찾으려는 업주 Id
     * @return ShopDetails 업주 Id로 찾은 가게의 상세정보
     */
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

    /**
     * 가게 검색
     * @param pageable 페이지 정보(page, limit, offset SpringDataWebProperties? org.springframework.data.domain.Pageable?
     * @param searchCondition  검색 상태
     * @return List<ShopDetails> 검색된 가게의 List
     */
    @Override
    public List<ShopDetails> searchShop(Pageable pageable, SearchCondition searchCondition) {
        return null;
    }
}
