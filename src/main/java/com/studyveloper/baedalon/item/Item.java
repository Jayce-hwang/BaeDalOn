package com.studyveloper.baedalon.item;

import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.shop.Shop;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;

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
    private int sortOrder;

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

    public void changeGroup(Group group) {
        this.group = group;
    }

    public void changeShop(Shop shop) {
        this.shop = shop;
    }
}
