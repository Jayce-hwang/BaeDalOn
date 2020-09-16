package com.studyveloper.baedalon.item;

import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.shop.Shop;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ITEMS")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SORT_ORDER")
    private long sortOrder;

    @Column(name = "STATUS")
    private ItemStatus status;

    @Column(name = "REPRESENTED")
    private boolean represented;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFED_AT")
    private LocalDateTime modifiedAt;

    @Builder
    public Item(String name, int price, String description, Group group, Shop shop) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = ItemStatus.ON_SALE;
        this.represented = false;

        if(group != null) {changeGroup(group);}
        if(shop != null) {changeShop(shop);}
    }

    public void editItem(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void swapSortOrder(Item targetItem) {
        if(shop.equals(targetItem.shop) && group.equals(targetItem.group)) {
            long originSortOrder = this.sortOrder;

            this.sortOrder = targetItem.sortOrder;
            targetItem.sortOrder = originSortOrder;
        } else {
            //TODO::익셉션 정의
            throw new RuntimeException();
        }
    }

    public void changeSortOrder(List<Item> items) {
        if(items.size() == 0) {
            this.sortOrder = 1;
        } else {
            this.sortOrder = items.get(items.size() -1).sortOrder + 1;

        }
    }

    public void changeGroup(Group group) {
        this.group = group;
    }

    public void changeShop(Shop shop) {
        this.shop = shop;
    }
}
