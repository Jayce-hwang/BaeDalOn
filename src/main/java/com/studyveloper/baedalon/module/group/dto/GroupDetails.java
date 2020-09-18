package com.studyveloper.baedalon.module.group.dto;

import com.studyveloper.baedalon.module.group.domain.Group;
import com.studyveloper.baedalon.module.group.domain.GroupStatus;
import lombok.Data;

@Data
public class GroupDetails {
    private Long id;
    private String name;
    private String description;
    private long sortOrder;
    private GroupStatus status;

    public GroupDetails() {}
    public GroupDetails(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.sortOrder = group.getSortOrder();
        this.status = group.getStatus();
    }
}
