package com.studyveloper.baedalon.module.item.dto;

import com.studyveloper.baedalon.module.item.domain.ItemStatus;
import com.studyveloper.baedalon.infra.util.SearchCondition;
import lombok.Data;

@Data
public class ItemSearchCondition extends SearchCondition {
    private Long id;
    private String name;
    private Integer price;
    private Integer priceGoe;
    private Integer priceLoe;
    private ItemStatus status;
    private Boolean represented;
    private Long groupId;
    private Long shopId;
}
