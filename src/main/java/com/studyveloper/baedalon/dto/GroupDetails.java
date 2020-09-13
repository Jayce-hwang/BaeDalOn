package com.studyveloper.baedalon.dto;

import com.studyveloper.baedalon.shop.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
import com.studyveloper.baedalon.shop.Shop;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

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
