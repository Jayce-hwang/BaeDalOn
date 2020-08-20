package com.studyveloper.baedalon.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.*;

@Entity
@Table(name="CUSTORMERS")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    @Enumerated(STRING)
    private CustomerStatus status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "LOGIN_ID")
    private String loginId;
}
