package com.studyveloper.baedalon.dto;

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
}
