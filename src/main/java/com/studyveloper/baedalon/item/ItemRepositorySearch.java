package com.studyveloper.baedalon.item;

import com.studyveloper.baedalon.item.dto.ItemDetails;
import com.studyveloper.baedalon.item.dto.ItemSearchCondition;
import com.studyveloper.baedalon.util.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositorySearch {
    Page<ItemDetails> searchItemDetails(Pageable pageable, ItemSearchCondition condition);
}
