package com.studyveloper.baedalon.group;

import com.studyveloper.baedalon.group.dto.GroupCreateDto;
import com.studyveloper.baedalon.group.dto.GroupDetails;
import com.studyveloper.baedalon.group.dto.GroupEditDto;

import java.util.List;

public interface GroupService {
    public Long createGroup(GroupCreateDto groupCreateDto, Long shopId);
    public void editGroup(Long groupId, GroupEditDto groupEditDto);
    public void swapGroupOrder(Long groupId, Long targetGroupId);
    public void deleteGroup(Long groupId);
    public GroupDetails findGroup(Long groupId);
    public List<GroupDetails> findGroups(Long shopId);
}
