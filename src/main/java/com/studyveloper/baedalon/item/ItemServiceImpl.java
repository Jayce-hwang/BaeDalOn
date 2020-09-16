package com.studyveloper.baedalon.item;

import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.group.GroupRepository;
import com.studyveloper.baedalon.item.dto.ItemCreateDto;
import com.studyveloper.baedalon.item.dto.ItemDetails;
import com.studyveloper.baedalon.item.dto.ItemEditDto;
import com.studyveloper.baedalon.shop.Shop;
import com.studyveloper.baedalon.shop.ShopRepository;
import com.studyveloper.baedalon.util.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final GroupRepository groupRepository;
    private final ShopRepository shopRepository;

    @Override
    public Long createItem(@NonNull ItemCreateDto itemCrateDto) {
        Shop shop = shopRepository.findById(itemCrateDto.getShopId()).orElseThrow(EntityNotFoundException::new);
        Group group = groupRepository.findById(itemCrateDto.getGroupId()).orElseThrow(EntityNotFoundException::new);
        //TODO:: shop 과 group이 맞는 연관관계인지 확인하는 로직 필요

        List<Item> items = itemRepository.findByShopIdAndGroupIdOrderBySortOrderAsc(shop.getId(), group.getId());

        Item item = Item.builder()
                .name(itemCrateDto.getName())
                .description(itemCrateDto.getDescription())
                .price(itemCrateDto.getPrice())
                .group(group)
                .shop(shop)
                .build();

        //TODO:: List객체 clone하여 사용하는 것에 대해 검증 필요
        item.changeSortOrder(items);

        item = itemRepository.save(item);

        return item.getId();
    }

    @Override
    public void editItem(@NonNull Long itemId, @NonNull ItemEditDto itemEditDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        //TODO::해당 수정요청이 올바른 shop, owner, group에서 이루어지는 것인지 확인필
        item.editItem(
                itemEditDto.getName(),
                itemEditDto.getPrice(),
                itemEditDto.getDescription()
        );
    }

    @Override
    public void deleteItem(@NonNull Long shopId, @NonNull Long groupId, @NonNull Long ownerId, @NonNull Long itemId) {
        //TODO::검증 로직 생각해보기
        itemRepository.deleteById(itemId);
    }

    @Override
    public void swapItem(@NonNull Long itemId, @NonNull Long targetItemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        Item targetItem = itemRepository.findById(targetItemId).orElseThrow(EntityNotFoundException::new);

        item.swapSortOrder(targetItem);
    }

    @Override
    public void hideItem(@NonNull Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        item.changeStatus(ItemStatus.SOLD_OUT);
    }

    @Override
    public void showItem(@NonNull Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        item.changeStatus(ItemStatus.ON_SALE);
    }

    @Override
    public void represent(@NonNull Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        item.changeRepresented(true);
    }

    @Override
    public void unrepresent(@NonNull Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        item.changeRepresented(false);
    }

    @Override
    public ItemDetails findItem(@NonNull Long itemId) {
        return null;
    }

    @Override
    public List<ItemDetails> findItems(@NonNull Long groupId) {
        return null;
    }

    @Override
    public List<ItemDetails> searchItem(@NonNull Pageable pageable, @NonNull SearchCondition searchCondition) {
        return null;
    }
}
