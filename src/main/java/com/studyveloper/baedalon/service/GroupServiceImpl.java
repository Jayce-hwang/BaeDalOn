package com.studyveloper.baedalon.service;

import com.studyveloper.baedalon.dto.GroupCreateDto;
import com.studyveloper.baedalon.dto.GroupDetails;
import com.studyveloper.baedalon.dto.GroupEditDto;
import com.studyveloper.baedalon.repository.GroupRepository;
import com.studyveloper.baedalon.shop.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
import com.studyveloper.baedalon.shop.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final EntityManager entityManager;


    @Override
    public Long createGroup(@NonNull GroupCreateDto groupCreateDto, @NonNull Long shopId) {
       List<Group> groupList = groupRepository.findByShopIdOrderBySortOrderDesc(shopId);

        //TODO: ShopRepository를 에서 findById를 통해 shop 엔티티를 가져오도록 변경 필요
        Shop shop = entityManager.find(Shop.class, shopId);

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
