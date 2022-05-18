package cn.sonui.onlinechat.service;

import cn.sonui.onlinechat.model.User;

public interface UserService {

    /**
     * 用户登录
     * @param key 用户名
     * @param password 密码
     * @return 登录失败返回空，成果返回token
     */
    String login(String key, String password);

    /**
     * 用户信息
     * @param token token
     * @return 用户个人信息
     */
    User self(String token);

    /**
     * 用户公开信息
     * @param key 用户名/邮箱
     * @return 用户信息
     */
    User info(String key);

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    Integer register(User user);
}
