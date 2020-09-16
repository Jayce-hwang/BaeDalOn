package com.studyveloper.baedalon.builder;

import com.studyveloper.baedalon.group.Group;
import com.studyveloper.baedalon.item.dto.ItemCreateDto;
import com.studyveloper.baedalon.shop.Shop;

import java.util.Random;

public class ItemBuilder {
    private static Random random;

    static {
        long seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static ItemCreateDto getItemCreateDto(Shop shop, Group group) {
        long value = random.nextLong();

        ItemCreateDto itemCreateDto = new ItemCreateDto();
        itemCreateDto.setName("item" + value);
        itemCreateDto.setDescription("desc" + value);
        itemCreateDto.setPrice(1000 + (int)value);
        itemCreateDto.setGroupId(group.getId());
        itemCreateDto.setShopId(shop.getId());

        return itemCreateDto;
    }
}
