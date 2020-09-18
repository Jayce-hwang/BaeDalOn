package com.studyveloper.baedalon.module.group.dto;

import com.studyveloper.baedalon.module.group.domain.GroupStatus;
import lombok.Data;

@Data
public class GroupEditDto {
    private String name;
    private String description;
    private GroupStatus status;
}
