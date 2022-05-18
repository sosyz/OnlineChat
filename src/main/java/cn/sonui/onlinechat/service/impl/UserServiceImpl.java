package cn.sonui.onlinechat.service.impl;

import cn.sonui.onlinechat.config.ProjectConfig;
import cn.sonui.onlinechat.mapper.UserMapper;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.UserService;
import cn.sonui.onlinechat.utils.SnowFlakeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tairitsu.ignotus.cache.CacheService;

import static com.alibaba.druid.util.Utils.md5;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    SnowFlakeFactory snowFlakeFactory;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CacheService cache;

    @Autowired
    ProjectConfig projectConfig;

    @Override
    public String login(String key, String password) {
        User res = userMapper.checkLoginByKey(key, password);
        if (res == null) {
            return null;
        }else {
            // 转为16进制
            String token = Long.toHexString(snowFlakeFactory.nextId());
            // 取md5
            token = md5(token + projectConfig.getSalt());
            cache.put(token, res);
            return token;
        }
    }

    @Override
    public User self(String token) {
        return cache.get(token, User.class);
    }

    @Override
    public User info(String key) {
        return userMapper.getUserInfoByKey(key);
    }

    @Override
    public Integer register(User user) {
        // 判断用户是否存在
        User ret = userMapper.checkLoginByModel(user);
        if (ret != null) {
            // 存在返回影响行数0 即失败
            return 0;
        }else {
            // 不存在返回sql执行影响行数
            return userMapper.register(user);
        }
    }
}
