package cn.sonui.onlinechat.service.impl;

import cn.sonui.onlinechat.mapper.GroupMapper;
import cn.sonui.onlinechat.model.Group;
import cn.sonui.onlinechat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Group query(String groupId) {
        return groupMapper.query(groupId);
    }

    @Override
    public Group[] queryByKey(String key) {
        return groupMapper.queryList(key);
    }

    @Override
    public Group[] queryAll() {
        return groupMapper.queryAll();
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
