package com.studyveloper.baedalon.item.dto;

import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.item.Item;
import com.studyveloper.baedalon.item.ItemStatus;
import com.studyveloper.baedalon.shop.Shop;
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
