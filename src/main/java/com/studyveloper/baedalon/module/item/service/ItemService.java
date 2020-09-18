package com.studyveloper.baedalon.module.item.service;

import com.studyveloper.baedalon.module.item.dto.ItemCreateDto;
import com.studyveloper.baedalon.module.item.dto.ItemDetails;
import com.studyveloper.baedalon.module.item.dto.ItemEditDto;
import com.studyveloper.baedalon.infra.util.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    public Long createItem(ItemCreateDto itemCrateDto);
    public void editItem(Long itemId, ItemEditDto itemEditDto);
    public void deleteItem(Long shopId, Long groupId, Long ownerId, Long itemId);
    public void swapItem(Long itemId, Long targetItemId);
    public void hideItem(Long itemId);
    public void showItem(Long itemId);
    public void represent(Long itemId);
    public void unrepresent(Long itemId);
    public ItemDetails findItem(Long itemId);
    public List<ItemDetails> findItems(Long groupId);
    public Page<ItemDetails> searchItem(Pageable pageable, SearchCondition searchCondition);
}
