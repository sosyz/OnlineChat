package cn.sonui.onlinechat.service;

import cn.sonui.onlinechat.model.User;

public interface GroupMembersService {
    /**
     * 添加群成员
     *
     * @param groupId  群id
     * @param doUserId 操作者id 0表示系统
     * @param userId   用户id
     * @return 是否成功 0成功 1不存在该群 2已经是群成员
     */
    Integer addGroupMember(String groupId, Long doUserId, Long userId);

    /**
     * 删除群成员
     *
     * @param groupId  群id
     * @param doUserId 操作者id ROOT表示系统
     * @param userId   用户id
     * @return 是否成功 1成功 -1不存在该群 -2不是群成员 -3非管理员
     */
    Integer deleteGroupMember(String groupId, Long doUserId, Long userId);

    /**
     * 查询群成员
     *
     * @param groupId 群id
     * @param userId  用户id
     * @return 群成员
     */
    User[] queryGroupMember(String groupId, Long userId);

    /**
     * 查询群成员权限
     *
     * @param groupId 群id
     * @param userId  用户id
     * @return 群成员
     */
    Integer queryGroupMemberPermission(String groupId, Long userId);

    /**
     * 退出群
     *
     * @param groupId 群id
     * @param userId  用户id
     * @return 退群结果 1成功 0失败 -1不存在该群 -2非该群成员 -3为群主禁止退出
     */
    Integer quitGroup(String groupId, Long userId);

    /**
     * 设置管理员
     *
     * @param groupId 群id
     * @param userId  用户id
     * @param pri     权限值
     * @return 设置结果 1成功 0失败 -1不存在该群 -2不是群成员 -3非群主
     */
    Integer setPri(String groupId, Long userId, Long pri);
}
