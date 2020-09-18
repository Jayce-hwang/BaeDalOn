package com.studyveloper.baedalon.builder;

import com.studyveloper.baedalon.module.group.domain.Group;
import com.studyveloper.baedalon.module.item.domain.Item;
import com.studyveloper.baedalon.module.item.dto.ItemCreateDto;
import com.studyveloper.baedalon.module.item.dto.ItemEditDto;
import com.studyveloper.baedalon.module.shop.domain.Shop;

import java.util.Random;

public class ItemBuilder {
    private static Random random;

    static {
        long seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public static Item getItem(Shop shop, Group group) {
        long value = random.nextLong();
        return Item.builder()
                .description("desc" + value)
                .name("name" + value)
                .price(1000 + (int)value)
                .group(group)
                .shop(shop)
                .build();
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

    public static ItemEditDto getItemEditDto() {
        long value = random.nextLong();

        ItemEditDto itemEditDto = new ItemEditDto();
        itemEditDto.setName("item-modified" + value);
        itemEditDto.setDescription("desc-modified" + value);
        itemEditDto.setPrice(1000 + (int)value);

        return itemEditDto;
    }
}
