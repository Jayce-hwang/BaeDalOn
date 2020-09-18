package com.studyveloper.baedalon.module.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.studyveloper.baedalon.module.item.domain.Item;
import com.studyveloper.baedalon.module.item.domain.ItemStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ItemDetails {
    private Long id;
    private String name;
    private int price;
    private String description;
    private long sortOrder;
    private ItemStatus status;
    private boolean represented;
    private Long groupId;
    private Long shopId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @QueryProjection
    public ItemDetails(Long id, String name, int price, String description,
                       long sortOrder, ItemStatus status, boolean represented,
                       Long groupId, Long shopId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sortOrder = sortOrder;
        this.status = status;
        this.represented = represented;
        this.groupId = groupId;
        this.shopId = shopId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ItemDetails(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.sortOrder = item.getSortOrder();
        this.status = item.getStatus();
        this.represented = item.isRepresented();
        this.groupId = item.getGroup().getId();
        this.shopId = item.getShop().getId();
        this.createdAt = item.getCreatedAt();
        this.modifiedAt = item.getModifiedAt();
    }
}
