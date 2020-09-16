package com.studyveloper.baedalon.item.dto;

import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.item.ItemStatus;
import com.studyveloper.baedalon.shop.Shop;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Data
public class ItemCreateDto {
    private String name;
    private int price;
    private String description;
    private Long groupId;
    private Long shopId;
}
