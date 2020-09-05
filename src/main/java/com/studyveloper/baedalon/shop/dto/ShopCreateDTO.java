package com.studyveloper.baedalon.shop.dto;

import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.user.Owner;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopCreateDTO {
    private Long id;
    private Owner owner;
    private String name;
    private String address;
    private String phone;
    private ShopStatus status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
