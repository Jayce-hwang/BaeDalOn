package com.studyveloper.baedalon.module.item.dto;

import lombok.Data;

@Data
public class ItemCreateDto {
    private String name;
    private int price;
    private String description;
    private Long groupId;
    private Long shopId;
}
