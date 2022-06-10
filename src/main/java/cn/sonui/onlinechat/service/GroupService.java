package cn.sonui.onlinechat.service;

import cn.sonui.onlinechat.model.Group;

import java.util.List;

/**
 * @author Sonui
 */
public interface GroupService {
    /**
     * 查询群组
     *
     * @param groupId 群组id
     * @return 群组实体
     */
    Group query(String groupId);

    /**
     * 查询群组
     *
     * @param key 查询关键词
     * @return 群组实体
     */
    List<Group> queryByKey(String key);

    /**
     * 查询群组
     *
     * @return 群组实体数组
     */
    List<Group> queryAll();

    /**
     * 创建群组
     *
     * @param groupId     群组id
     * @param groupName   群组名称
     * @param groupAvatar 群组头像
     * @return 是否插入成功
     */
    Integer create(String groupId, String groupName, String groupAvatar);

    /**
     * 更新群组
     *
     * @param groupId     群组id
     * @param groupName   群组名称
     * @param groupAvatar 群组头像
     * @return 更新结果
     */
    Integer update(String groupId, String groupName, String groupAvatar);

    /**
     * 删除群组
     *
     * @param groupId 群组id
     * @return 删除结果
     */
    Integer delete(String groupId);


    /**
     * 查询所在群列表
     *
     * @param userId  用户id
     * @return 群列表
     */
    List<Group> queryUserInGroupList(Long userId);
}
