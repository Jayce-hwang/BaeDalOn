package com.studyveloper.baedalon.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDER_ITEMS")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    @Column(name= "ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "ITEM_PRICE")
    private int itemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;
}
