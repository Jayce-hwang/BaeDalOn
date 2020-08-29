package com.studyveloper.baedalon.dto;

import com.studyveloper.baedalon.shop.GroupStatus;
import lombok.Data;

@Data
public class GroupEditDto {
    private String name;
    private String description;
    private GroupStatus status;
}
