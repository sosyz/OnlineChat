package cn.sonui.onlinechat.service;

import cn.sonui.onlinechat.model.Group;

public interface GroupService {
    /**
     * 查询群组
     * @param groupId 群组id
     * @return 群组实体
     */
    Group query(String groupId);

    /**
     * 查询群组
     * @param key 查询关键词
     * @return 群组实体
     */
    Group[] queryByKey(String key);

    /**
     * 查询群组
     * @return 群组实体数组
     */
    Group[] queryAll();
    /**
     * 创建群组
     *
     * @param groupId 群组id
     * @param groupName 群组名称
     * @param groupAvatar 群组头像
     */
    Integer create(String groupId, String groupName, String groupAvatar);

    /**
     * 更新群组
     *
     * @param groupId 群组id
     * @param groupName 群组名称
     * @param groupAvatar 群组头像
     * @return 更新结果
     */
    Integer update(String groupId, String groupName, String groupAvatar);

    /**
     * 删除群组
     * @param groupId 群组id
     * @return 删除结果
     */
    Integer delete(String groupId);
}
