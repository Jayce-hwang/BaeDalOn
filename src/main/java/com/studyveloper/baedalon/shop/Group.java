package com.studyveloper.baedalon.shop;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "GROUPS")
@Getter
@NoArgsConstructor
public class Group {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SORT_ORDER")
    private long sortOrder;

    @Column(name = "STATUS")
    private GroupStatus status;

    @Column(name = "CREATED_AT")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "MODIFED_AT")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @Builder
    public Group(String name, String description, Shop shop) {
        this.name = name;
        this.description = description;
        this.status = GroupStatus.SHOWN;

        if( shop != null) {
            setShop(shop);
        }
    }

    public void editGroup(String name, String description, GroupStatus status) {
        changeName(name);
        changeDescription(description);
        changeGroupStatus(status);
    }

    public void swapSortOrder(Group targetGroup) {
        long originSortOrder = this.sortOrder;

        this.sortOrder = targetGroup.sortOrder;
        targetGroup.sortOrder = originSortOrder;
    }

    public void changeSortOrder(List<Group> groupList) {
        int size = groupList.size();

        if (size == 0) {
            this.sortOrder = 1;
        } else {
            this.sortOrder = groupList.get(size -1)
                    .getSortOrder() + 1;
        }
    }

    public void changeGroupStatus(GroupStatus status){
        this.status = status;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void chagneSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setShop(Shop shop){
        this.shop = shop;
    }
}
