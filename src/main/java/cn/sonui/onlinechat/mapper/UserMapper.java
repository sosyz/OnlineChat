package cn.sonui.onlinechat.mapper;

import cn.sonui.onlinechat.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 用户注册
     *
     * @param user 用户实体
     * @return 注册成功返回1，否则返回0
     */
    @Insert("INSERT INTO `users` " +
            "VALUES " +
            "(#{uid},#{nickName}, #{userName}, #{password}, #{salt}, #{avatar}, " +
            "#{email}, #{grade}, #{readme}, #{registerTime}, #{lastLoginTime}, #{lastLoginIp}, #{privateId})")
    @Result(column = "nick_name", property = "nickName", javaType = String.class)
    @Result(column = "user_name", property = "userName", javaType = String.class)
    @Result(column = "register_time", property = "registerTime", javaType = Date.class)
    @Result(column = "last_login_time", property = "lastLoginTime", javaType = Date.class)
    @Result(column = "last_login_ip", property = "lastLoginIp", javaType = String.class)
    @Result(column = "private_id", property = "privateId", javaType = Long.class)
    int register(User user);

    /**
     * 用户登录
     *
     * @param user 用户实体
     * @return 用户信息
     */
    @Select("SELECT " +
            "* " +
            "FROM " +
            "`users` " +
            "WHERE " +
            "`user_name` = #{userName} AND " +
            "`password` = #{password}")
    @Result(column = "nick_name", property = "nickName", javaType = String.class)
    @Result(column = "user_name", property = "userName", javaType = String.class)
    @Result(column = "register_time", property = "registerTime", javaType = Date.class)
    @Result(column = "last_login_time", property = "lastLoginTime", javaType = Date.class)
    @Result(column = "last_login_ip", property = "lastLoginIp", javaType = String.class)
    @Result(column = "private_id", property = "privateId", javaType = Long.class)
    User checkLoginByModel(User user);

    /**
     * 用户登录
     *
     * @param key      用户名/邮箱
     * @param password 密码
     * @return 用户信息
     */
    @Select("SELECT " +
            "* " +
            "FROM " +
            "`users` " +
            "WHERE " +
            "(`user_name` = #{key} OR `email` = #{key}) AND " +
            "`password` = #{password}")
    @Result(column = "nick_name", property = "nickName", javaType = String.class)
    @Result(column = "user_name", property = "userName", javaType = String.class)
    @Result(column = "register_time", property = "registerTime", javaType = Date.class)
    @Result(column = "last_login_time", property = "lastLoginTime", javaType = Date.class)
    @Result(column = "last_login_ip", property = "lastLoginIp", javaType = String.class)
    @Result(column = "private_id", property = "privateId", javaType = Long.class)
    User checkLoginByKey(@Param("key") String key, @Param("password") String password);

    /**
     * 获取用户信息
     *
     * @param uid 用户id
     * @return 用户信息
     */
    @Select("SELECT " +
            "* " +
            "FROM " +
            "`users` " +
            "WHERE " +
            "`uid` = #{uid}"
    )
    @Result(column = "nick_name", property = "nickName", javaType = String.class)
    @Result(column = "user_name", property = "userName", javaType = String.class)
    @Result(column = "register_time", property = "registerTime", javaType = Date.class)
    @Result(column = "last_login_time", property = "lastLoginTime", javaType = Date.class)
    @Result(column = "last_login_ip", property = "lastLoginIp", javaType = String.class)
    @Result(column = "private_id", property = "privateId", javaType = Long.class)
    User getUserInfoByUid(@Param("uid") Long uid);

    /**
     * 获取用户信息
     *
     * @param key 用户名或邮箱
     * @return 用户信息
     */
    @Select("SELECT " +
            "* " +
            "FROM " +
            "`users` " +
            "WHERE " +
            "`user_name` = #{key} OR `email` = #{key}")
    @Result(column = "nick_name", property = "nickName", javaType = String.class)
    @Result(column = "user_name", property = "userName", javaType = String.class)
    @Result(column = "register_time", property = "registerTime", javaType = Date.class)
    @Result(column = "last_login_time", property = "lastLoginTime", javaType = Date.class)
    @Result(column = "last_login_ip", property = "lastLoginIp", javaType = String.class)
    @Result(column = "private_id", property = "privateId", javaType = Long.class)
    User getUserInfoByKey(@Param("key") String key);

    /**
     * 获取用户列表
     */
    @Select("SELECT " +
            "* " +
            "FROM " +
            "`users` "
    )
    @Result(column = "nick_name", property = "nickName", javaType = String.class)
    @Result(column = "user_name", property = "userName", javaType = String.class)
    @Result(column = "register_time", property = "registerTime", javaType = Date.class)
    @Result(column = "last_login_time", property = "lastLoginTime", javaType = Date.class)
    @Result(column = "last_login_ip", property = "lastLoginIp", javaType = String.class)
    @Result(column = "private_id", property = "privateId", javaType = Long.class)
    List<User> getUserList();

    /**
     * 更新用户信息
     *
     * @param user 用户实体
     * @return 更新成功返回1，否则返回0
     */
    @Update("UPDATE `users` " +
            "SET " +
            "`nick_name` = #{nickName}, " +
            "`password` = #{password}, " +
            "`avatar` = #{avatar}, " +
            "`email` = #{email}, " +
            "`grade` = #{grade}, " +
            "`readme` = #{readme}, " +
            "`last_login_time` = #{lastLoginTime}, " +
            "`last_login_ip` = #{lastLoginIp}, " +
            "`private_id` = #{privateId} " +
            "WHERE " +
            "`uid` = #{uid}")
    int updateUser(User user);

    /**
     * 查询是否已存在该用户名
     *
     * @param username 用户名
     * @return 存在返回1，否则返回0
     */
    @Select("SELECT " +
            "COUNT(`user_name`) " +
            "FROM " +
            "`users` " +
            "WHERE " +
            "`user_name` = #{username}")
    int checkUserName(@Param("username") String username);
}
