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
        //첫번째 Group이 들어가실 시에는 아무 Group도 등록되어있지 않기 때문에 집단함수의 결과값 행이 없음
        // 해결책 1. Null값을 리턴받으면 sortOrder를 1로 세팅한다.
        // 해결책 2. 먼저 sortOrder값을 디폴트값으로 정해서 save를 한 후에 영속 상태인 group의 sortOrder를 변경한다.
        // 해결책 2의 문제 => save후에 한번 더 값이 바뀌기 때문에 더티체킹을 통한 db반영이 한번더 이루어져야함
        // 어떻게 할지?
        Long sortOrder = new Long(1);

        try {
            sortOrder = groupRepository.findGroupCountByShopId(shopId) + 1;
        } catch (NullPointerException exception){
            exception.printStackTrace();
        }

        //shopId로 Shop을 가져오던가 or Shop에서 그룹을 추가하던가 둘중하나는 해야함
        Shop shop = entityManager.find(Shop.class, shopId);

        Group group = Group.builder()
                .name(groupCreateDto.getName())
                .description(groupCreateDto.getDescription())
                .shop(shop)
                .build();

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
