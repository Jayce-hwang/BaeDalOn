package com.studyveloper.baedalon.module.group.service;

import com.studyveloper.baedalon.module.group.dao.GroupRepository;
import com.studyveloper.baedalon.module.group.domain.Group;
import com.studyveloper.baedalon.module.group.dto.GroupCreateDto;
import com.studyveloper.baedalon.module.group.dto.GroupDetails;
import com.studyveloper.baedalon.module.group.dto.GroupEditDto;
import com.studyveloper.baedalon.module.shop.domain.Shop;
import com.studyveloper.baedalon.module.shop.dao.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final ShopRepository shopRepository;

    @Override
    public Long createGroup(@NonNull GroupCreateDto groupCreateDto, @NonNull Long shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(EntityNotFoundException::new);

        List<Group> groupList = groupRepository.findByShopIdOrderBySortOrderAsc(shopId);

        Group group = Group.builder()
                .name(groupCreateDto.getName())
                .description(groupCreateDto.getDescription())
                .shop(shop)
                .build();

        group.changeSortOrder(groupList);

        group = groupRepository.save(group);

        return group.getId();
    }

    @Override
    public void editGroup(@NonNull Long groupId, @NonNull GroupEditDto groupEditDto) {
        Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

        group.editGroup(
                groupEditDto.getName(),
                groupEditDto.getDescription(),
                groupEditDto.getStatus()
        );
    }

    @Override
    public void swapGroupOrder(@NonNull Long groupId, @NonNull Long targetGroupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
        Group targetGroup = groupRepository.findById(targetGroupId).orElseThrow(EntityNotFoundException::new);

        //TODO:: 같은 샵의 그룹이 아닐 경우에 대한 예외처리 추가 필

        group.swapSortOrder(targetGroup);
    }

    @Override
    public void deleteGroup(@NonNull Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public GroupDetails findGroup(@NonNull Long groupId){
        Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

        return new GroupDetails(group);
    }

    @Override
    public List<GroupDetails> findGroups(@NonNull Long shopId) {
        List<Group> result = groupRepository.findByShopId(shopId);
        List<GroupDetails> groupDetailsList = new ArrayList<GroupDetails>();

        for(Group group : result) {
            groupDetailsList.add(new GroupDetails(group));
        }

        return groupDetailsList;
    }
}
