package com.studyveloper.baedalon.builder;

import com.studyveloper.baedalon.module.group.domain.Group;
import com.studyveloper.baedalon.module.group.dto.GroupCreateDto;
import com.studyveloper.baedalon.module.group.dto.GroupEditDto;
import com.studyveloper.baedalon.module.group.domain.GroupStatus;
import com.studyveloper.baedalon.module.shop.domain.Shop;

import java.util.Random;

public class GroupBuilder {
    private static Random random;

    static {
        long seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static Group getGroup(Shop shop) {
        return Group.builder()
                .name("group" + random.nextLong())
                .description("description" + random.nextLong())
                .shop(shop)
                .build();
    }

    public static GroupCreateDto getGroupCreateDto() {
        long value = random.nextLong();

        GroupCreateDto groupCreateDto = new GroupCreateDto();
        groupCreateDto.setName("group" + value);
        groupCreateDto.setDescription("description" + value);

        return groupCreateDto;
    }

    public static GroupEditDto getGroupEditDto() {
        long value = random.nextLong();

        GroupEditDto groupEditDto = new GroupEditDto();
        groupEditDto.setName("editedGroup" + value);
        groupEditDto.setStatus(GroupStatus.HIDDEN);
        groupEditDto.setDescription("editedDescription" + value);

        return groupEditDto;
    }
}
