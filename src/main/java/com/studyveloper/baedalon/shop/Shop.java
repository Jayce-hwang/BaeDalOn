package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.user.Owner;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "SHOPS")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "OWNER_ID")
    private Owner owner;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "STATUS")
    @Enumerated(STRING)
    private ShopStatus status;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;
}
