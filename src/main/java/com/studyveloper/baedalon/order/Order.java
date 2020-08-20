package com.studyveloper.baedalon.order;

import com.studyveloper.baedalon.shop.Shop;
import com.studyveloper.baedalon.user.Customer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ORDERS")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Enumerated(STRING)
    @Column(name = "TYPE")
    private OrderType orderType;

    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;

    @Column(name = "REQUESTS") // 요청사항
    private String requests;

    @Column(name = "ORDERED_AT")
    private LocalDateTime orderedAt;

    @Column(name = "RECEIPTED_AT")
    private LocalDateTime receiptedAt;

    @Column(name = "DELIVERED_AT")
    private LocalDateTime deliveredAt;

    @Column(name = "STATUS")
    @Enumerated(STRING)
    private OrderStatus status;

    @Column(name = "SHOP_NAME")
    private String shopName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_BY")
    private Customer customer;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "order", fetch = LAZY)
    private List<OrderItem> orderItems;
}
