package com.studyveloper.baedalon.module.item.dao;

import com.studyveloper.baedalon.module.item.dto.ItemDetails;
import com.studyveloper.baedalon.module.item.dto.ItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositorySearch {
    Page<ItemDetails> searchItemDetails(Pageable pageable, ItemSearchCondition condition);
}
