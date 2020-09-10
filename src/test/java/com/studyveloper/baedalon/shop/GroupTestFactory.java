package com.studyveloper.baedalon.shop;

import com.studyveloper.baedalon.dto.GroupCreateDto;

import java.util.Random;

public class GroupTestFactory {
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
}
