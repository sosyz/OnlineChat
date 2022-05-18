package cn.sonui.onlinechat.service.impl;

import cn.sonui.onlinechat.mapper.GroupMapper;
import cn.sonui.onlinechat.mapper.GroupMembersMapper;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.GroupMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMembersServiceImpl implements GroupMembersService {
    @Autowired
    private GroupMembersMapper groupMembersMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Integer addGroupMember(String groupId, Long doUserId, Long userId) {
        // 是否存在群
        if (groupMapper.query(groupId) == null) {
            return -1;
        }

        // 是否存在群成员
        if (groupMembersMapper.isExistGroupMember(groupId, userId) > 0) {
            return -2;
        }

        // 是否为系统拉入
        if (doUserId == 1L) {
            return groupMembersMapper.addGroupMember(groupId, userId, 0L);
        } else {
            // 检查是否为管理员
            if (groupMembersMapper.getGroupMemberPri(groupId, doUserId) >= 1) {
                return groupMembersMapper.addGroupMember(groupId, userId, 0L);
            } else {
                return -3;
            }
        }
    }

    @Override
    public Integer deleteGroupMember(String groupId, Long doUserId, Long userId) {
        if (groupMapper.query(groupId) == null) {
            return -1;
        } else if (groupMembersMapper.isExistGroupMember(groupId, userId) == 1) {
            return groupMembersMapper.deleteGroupMember(groupId, userId);
        } else {
            return -2;
        }
    }

    @Override
    public User[] queryGroupMember(String groupId, Long userId) {
        return groupMembersMapper.getGroupMembers(groupId);
    }

    @Override
    public Integer queryGroupMemberPermission(String groupId, Long userId) {
        return groupMembersMapper.getGroupMemberPri(groupId, userId);
    }

    @Override
    public Integer quitGroup(String groupId, Long userId) {
        // 检测是否存在群
        if (groupMapper.query(groupId) == null) {
            return -1;
        }

        // 检测是否存在群成员
        if (groupMembersMapper.isExistGroupMember(groupId, userId) == null) {
            return -2;
        }

        // 检测是否是群主
        if (groupMembersMapper.getGroupMemberPri(groupId, userId) == 2) {
            return -3;
        }

        return groupMembersMapper.deleteGroupMember(groupId, userId);
    }

    @Override
    public Integer setPri(String groupId, Long userId, Long pri) {
        // 检测是否存在群
        if (groupMapper.query(groupId) == null) {
            return -1;
        }

        // 检测是否存在群成员
        if (groupMembersMapper.isExistGroupMember(groupId, userId) == null) {
            return -2;
        }

        // 检测是否是群主
        if (groupMembersMapper.getGroupMemberPri(groupId, userId) != 2) {
            return -3;
        }

        return groupMembersMapper.setGroupMemberPri(groupId, userId, pri);
    }
}
