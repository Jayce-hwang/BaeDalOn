package com.studyveloper.baedalon.module.shop.domain;

import com.studyveloper.baedalon.module.user.domain.Owner;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "SHOPS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;


    @Builder
    public Shop(Long id, Owner owner, String name, String address, String phone, ShopStatus status, String description, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


    public void open(){
        this.status = ShopStatus.OPEN;
    }
    public void close(){
        this.status = ShopStatus.CLOSE;
    }
    public void modify(String name, String address, String phone, String description){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.status = ShopStatus.CLOSE;
    }
}
