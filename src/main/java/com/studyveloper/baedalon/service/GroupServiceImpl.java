package com.studyveloper.baedalon.service;

import com.studyveloper.baedalon.dto.GroupCreateDto;
import com.studyveloper.baedalon.dto.GroupDetails;
import com.studyveloper.baedalon.dto.GroupEditDto;
import com.studyveloper.baedalon.repository.GroupRepository;
import com.studyveloper.baedalon.shop.Group;
import com.studyveloper.baedalon.shop.GroupStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;

    @Override
    public Long createGroup(GroupCreateDto groupCreateDto, Long shopId) {
        long sortOrder = groupRepository.findGroupCountByShopId(shopId) + 1;

        Group group = Group.builder()
                .name(groupCreateDto.getName())
                .description(groupCreateDto.getDescription())
                .sortOrder(sortOrder)
                .build();

        groupRepository.save(group);

        return group.getId();
    }

    @Override
    public void editGroup(Long groupId, GroupEditDto groupEditDto) {
        Group group = groupRepository.getOne(groupId);

        String name = groupEditDto.getName();
        String description = groupEditDto.getDescription();
        GroupStatus status = groupEditDto.getStatus();

        group.changeName(name);
        group.changeDescription(description);
        group.changeGroupStatus(status);
    }

    @Override
    public void swapGroupOrder(Long groupId, Long targetGroupId) {
        Group group = groupRepository.getOne(groupId);
        Group targetGroup = groupRepository.getOne(targetGroupId);

        long sortOrder = group.getSortOrder();
        long targetSortOrder = targetGroup.getSortOrder();

        group.chagneSortOrder(targetSortOrder);
        targetGroup.chagneSortOrder(sortOrder);
    }

    @Override
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public GroupDetails findGroup(Long groupId){
        Group result = groupRepository.findById(groupId)
                .get();

        GroupDetails groupDetails = new GroupDetails();

        groupDetails.setDescription(result.getDescription());
        groupDetails.setId(result.getId());
        groupDetails.setName(result.getName());
        groupDetails.setSortOrder(result.getSortOrder());
        groupDetails.setStatus(result.getStatus());

        return groupDetails;
    }

    @Override
    public List<GroupDetails> findGroups(Long shopId) {
        List<Group> results = groupRepository.findByShopId(shopId);
        List<GroupDetails> groupDetailsList = new ArrayList<GroupDetails>();

        for(Group result : results) {
            GroupDetails groupDetails = new GroupDetails();
            groupDetails.setDescription(result.getDescription());
            groupDetails.setId(result.getId());
            groupDetails.setName(result.getName());
            groupDetails.setSortOrder(result.getSortOrder());
            groupDetails.setStatus(result.getStatus());

            groupDetailsList.add(groupDetails);
        }

        return groupDetailsList;
    }
}
