package com.studyveloper.baedalon.module.item.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studyveloper.baedalon.module.item.domain.ItemStatus;
import com.studyveloper.baedalon.module.item.dto.ItemDetails;
import com.studyveloper.baedalon.module.item.dto.ItemSearchCondition;
import com.studyveloper.baedalon.module.item.dto.QItemDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.studyveloper.baedalon.module.group.domain.QGroup.group;
import static com.studyveloper.baedalon.module.item.domain.QItem.item;
import static com.studyveloper.baedalon.module.shop.domain.QShop.shop;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ItemRepositorySearchImpl implements ItemRepositorySearch{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ItemDetails> searchItemDetails(Pageable pageable, ItemSearchCondition condition) {
        QueryResults<ItemDetails> result = queryFactory
                .select(new QItemDetails(
                        item.id,
                        item.name,
                        item.price,
                        item.description,
                        item.sortOrder,
                        item.status,
                        item.represented,
                        item.group.id,
                        item.shop.id,
                        item.createdAt,
                        item.modifiedAt
                ))
                .from(item)
                .leftJoin(item.shop, shop)
                .leftJoin(item.group, group)
                .where(
                        idEq(condition.getId()),
                        nameLike(condition.getName()),
                        priceGoe(condition.getPriceGoe()),
                        priceLoe(condition.getPriceLoe()),
                        statusEq(condition.getStatus()),
                        isRepresented(condition.getRepresented()),
                        groupIdEq(condition.getGroupId()),
                        shopIdEq(condition.getShopId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ItemDetails> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<ItemDetails>(content, pageable, total);
    }

    private BooleanExpression idEq(Long id) {
        return id != null ? item.id.eq(id) : null;
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? item.name.like(name) : null;
    }

    private BooleanExpression priceGoe(Integer price) {
        return price != null ? item.price.goe(price) : null;
    }

    private BooleanExpression priceLoe(Integer price) {
        return price != null ? item.price.loe(price) : null;
    }

    private BooleanExpression statusEq(ItemStatus status) {
        return status != null ? item.status.eq(status) : null;
    }

    private BooleanExpression isRepresented(Boolean represented) {
        return represented != null ? item.represented.eq(represented) : null;
    }

    private BooleanExpression groupIdEq(Long groupId) {
        return groupId != null ? item.group.id.eq(groupId) : null;
    }

    private BooleanExpression shopIdEq(Long shopId) {
        return shopId != null ? item.shop.id.eq(shopId) : null;
    }
}
