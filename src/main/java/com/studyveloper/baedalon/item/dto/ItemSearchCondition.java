package com.studyveloper.baedalon.item.dto;

import com.studyveloper.baedalon.item.ItemStatus;
import com.studyveloper.baedalon.util.SearchCondition;
import lombok.Data;

import java.time.LocalDateTime;

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
