package com.studyveloper.baedalon.item;

import com.studyveloper.baedalon.item.dto.ItemCreateDto;
import com.studyveloper.baedalon.item.dto.ItemDetails;
import com.studyveloper.baedalon.item.dto.ItemEditDto;
import com.studyveloper.baedalon.util.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;


    @Override
    public Long createItem(ItemCreateDto itemCrateDto) {
        return null;
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
