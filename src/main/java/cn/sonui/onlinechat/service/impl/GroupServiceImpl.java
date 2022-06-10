package cn.sonui.onlinechat.service.impl;

import cn.sonui.onlinechat.mapper.GroupMapper;
import cn.sonui.onlinechat.model.Group;
import cn.sonui.onlinechat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Group query(String groupId) {
        return groupMapper.query(groupId);
    }

    @Override
    public List<Group> queryByKey(String key) {
        return groupMapper.queryList(key);
    }

    @Override
    public List<Group> queryAll() {
        return groupMapper.queryAll();
    }

    @Override
    public List<Group> queryUserInGroupList(Long userId){
        return groupMapper.queryUserInGroupList(userId);
    }

    @Override
    public Integer create(String groupId, String groupName, String groupAvatar) {
        return groupMapper.create(groupId, groupName, groupAvatar);
    }

    @Override
    public Integer update(String groupId, String groupName, String groupAvatar) {
        return groupMapper.update(groupId, groupName, groupAvatar);
    }

    @Override
    public Integer delete(String groupId) {
        return groupMapper.delete(groupId);
    }
}
