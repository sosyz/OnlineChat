package cn.sonui.onlinechat.service;

import cn.sonui.onlinechat.VO.UserInfoVO;
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
    User Self(String token);
}
