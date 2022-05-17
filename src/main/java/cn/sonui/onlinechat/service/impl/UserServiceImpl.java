package cn.sonui.onlinechat.service.impl;

import cn.sonui.onlinechat.VO.UserInfoVO;
import cn.sonui.onlinechat.mapper.UserMapper;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.UserService;
import cn.sonui.onlinechat.utils.SnowFlakeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tairitsu.ignotus.cache.CacheService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    SnowFlakeFactory snowFlakeFactory;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CacheService cache;

    /**
     * 用户登录
     *
     * @param key 用户名
     * @param password 密码
     * @return 成功返回token，失败返回null
     */
    @Override
    public String login(String key, String password) {
        User res = userMapper.checkLoginByKey(key, password);
        if (res == null) {
            return null;
        }else {
            // 转为16进制
            String token = Long.toHexString(snowFlakeFactory.nextId());
            cache.put(token, res);
            return token;
        }
    }

    /**
     * 用户信息
     *
     * @param token token
     * @return 用户个人信息
     */
    @Override
    public User Self(String token) {
        return cache.get(token, User.class);
    }
}
