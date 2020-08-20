package com.studyveloper.baedalon.shop;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "CATEGORIES_SHOPS")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryShop {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

}
