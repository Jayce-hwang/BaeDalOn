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
    public Long createItem(ItemCreateDto itemCrateDto) {
        Shop shop = shopRepository.findById(itemCrateDto.getShopId()).orElseThrow(EntityNotFoundException::new);
        Group group = groupRepository.findById(itemCrateDto.getGroupId()).orElseThrow(EntityNotFoundException::new);

        List<Item> items = itemRepository.findByShopIdOrderBySortOrderAsc(shop.getId());

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
    public void editItem(Long itemId, ItemEditDto itemEditDto) {

    }

    @Override
    public void deleteItem(Long shopId, Long groupId, Long ownerId, Long itemId) {

    }

    @Override
    public void swapItem(Long itemId, Long targetItemId) {

    }

    @Override
    public void hideItem(Long itemId) {

    }

    @Override
    public void showItem(Long itemId) {

    }

    @Override
    public void represent(Long itemId) {

    }

    @Override
    public void unrepresent(Long itemId) {

    }

    @Override
    public ItemDetails findItem(Long itemId) {
        return null;
    }

    @Override
    public List<ItemDetails> findItems(Long groupId) {
        return null;
    }

    @Override
    public List<ItemDetails> searchItem(Pageable pageable, SearchCondition searchCondition) {
        return null;
    }
}
