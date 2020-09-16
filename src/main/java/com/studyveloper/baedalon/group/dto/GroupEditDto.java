package com.studyveloper.baedalon.group.dto;

import com.studyveloper.baedalon.group.GroupStatus;
import lombok.Data;

@Data
public class GroupEditDto {
    private String name;
    private String description;
    private GroupStatus status;
}
