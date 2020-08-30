package com.studyveloper.baedalon.dto;

import com.studyveloper.baedalon.shop.ShopStatus;
import com.studyveloper.baedalon.user.Owner;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
