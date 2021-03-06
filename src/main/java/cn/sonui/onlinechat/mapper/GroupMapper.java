package cn.sonui.onlinechat.mapper;

import cn.sonui.onlinechat.model.Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sonui
 */
@Repository
public interface GroupMapper {
    /**
     * 创建群组
     *
     * @param groupId     群组id
     * @param groupName   群组名称
     * @param groupAvatar 群组头像
     * @return 是否插入成功
     */
    @Insert("INSERT INTO `groups`" +
            "(`id`, `name`, `avatar`)" +
            "VALUES" +
            "(#{groupId}, #{groupName}, #{groupAvatar})")
    Integer create(String groupId, String groupName, String groupAvatar);

    /**
     * 根据群组id查询群组信息
     *
     * @param groupId 群组id
     * @return 群组信息
     */
    @Select("SELECT * FROM `groups`" +
            " where id = #{groupId}")
    Group query(String groupId);

    /**
     * 查询群列表
     *
     * @param key 查询关键字
     * @return 群列表
     */
    @Select("SELECT * FROM `groups`" +
            " where name like #{key}")
    List<Group> queryList(String key);

    /**
     * 查询用户所在群列表
     *
     * @param userId 用户id
     * @return 群列表
     */
    @Select("SELECT * FROM `groups`" +
            " where id in (select group_id from group_members where user_id = #{userId})")
    List<Group> queryUserInGroupList(Long userId);

    /**
     * 查询群列表
     *
     * @return 群列表
     */
    @Select("SELECT * FROM `groups`")
    List<Group> queryAll();

    /**
     * 修改群信息
     *
     * @param groupId   群组id
     * @param groupName 群名称
     * @param avatar    群头像
     * @return 修改结果
     */
    @Select("UPDATE `groups`" +
            " SET `name` = #{groupName}, `avatar` = #{avatar}" +
            " WHERE `id` = #{groupId}")
    Integer update(String groupId, String groupName, String avatar);

    /**
     * 删除群组
     *
     * @param groupId 群组id
     * @return 解散结果
     */
    @Delete("DELETE FROM `groups`" +
            " WHERE `id` = #{groupId}")
    Integer delete(String groupId);
}
