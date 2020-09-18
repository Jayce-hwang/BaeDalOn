package com.studyveloper.baedalon.module.user.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.*;

@Entity
@Table(name = "OWNERS")
@Getter
public class Owner {

    @Id @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    @Enumerated(STRING)
    private OwnerStatus status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;
    
    protected Owner() {
    	status = OwnerStatus.ACTIVATED;
    	createdAt = LocalDateTime.now();
    	modifiedAt = LocalDateTime.now();
    }
    
    @Builder
    public Owner(String email, String phone, String name, String password) {
    	this();
    	this.email = email;
    	this.phone = phone;
    	this.name = name;
    	this.password = password;
    }
    
    public void update(String phone, String name, String password) {
    	this.phone = phone;
    	this.name = name;
    	this.password = password;
    	this.modifiedAt = LocalDateTime.now();
    }
    
    public void inactivate() {
    	this.status = OwnerStatus.INACTIVATED;
    }
}
