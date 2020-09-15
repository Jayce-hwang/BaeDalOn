package com.studyveloper.baedalon.group.dto;

import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
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
