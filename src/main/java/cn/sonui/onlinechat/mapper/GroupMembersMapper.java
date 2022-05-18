package cn.sonui.onlinechat.mapper;

import cn.sonui.onlinechat.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersMapper {
    /**
     * 新建群员关系
     * @param groupId 群id
     * @param userId 用户id
     * @return 是否成功
     */
    @Insert("insert into group_members(group_id,user_id, pri, join_time) values(#{groupId},#{userId}, #{privilege}, NOW())")
    Integer addGroupMember(String groupId, Long userId, Long privilege);

    /**
     * 删除群员关系
     * @param groupId 群id
     * @param userId 用户id
     * @return 是否成功
     */
    @Delete("delete from group_members where group_id = #{groupId} and user_id = #{userId}")
    Integer deleteGroupMember(String groupId, Long userId);

    /**
     * 设置群员权限
     *
     * @param groupId 群id
     * @param userId 用户id
     * @param privilege 权限 0群员 1管理 2群主
     * @return 是否成功
     */
    @Update("update group_members set pri = #{privilege} where group_id = #{groupId} and user_id = #{userId}")
    Integer setGroupMemberPri(String groupId, Long userId, Long privilege);

    /**
     * 查询群员权限
     *
     * @param groupId 群id
     * @param userId 用户id
     * @return 权限 0群员 1管理 2群主
     */
    @Select("select pri from group_members where group_id = #{groupId} and user_id = #{userId}")
    Integer getGroupMemberPri(String groupId, Long userId);

    /**
     * 查询是否存在群成员
     *
     * @param groupId 群id
     * @param userId 用户id
     * @return 是否存在
     */
    @Select("select count(user_id) from group_members where group_id = #{groupId} and user_id = #{userId}")
    Integer isExistGroupMember(String groupId, Long userId);

    /**
     * 查询群成员列表
     *
     * @param groupId 群id
     * @return 群成员列表
     */
    @Select("select a.user_id, b.* from group_members as a, users as b where a.group_id = #{groupId} and a.user_id = b.uid")
    User[] getGroupMembers(String groupId);


}
