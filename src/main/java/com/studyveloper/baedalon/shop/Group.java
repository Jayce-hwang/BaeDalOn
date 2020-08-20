package com.studyveloper.baedalon.shop;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "GROUPS")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SORT_ORDER")
    private int sortOrder;

    @Column(name = "STATUS")
    private GroupStatus status;
}
