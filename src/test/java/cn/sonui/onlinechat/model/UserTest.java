package cn.sonui.onlinechat.model;

import cn.sonui.onlinechat.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest    //缓存MybatisTest注解
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //这个是启用自己配置的数据元，不加则采用虚拟数据源
@Rollback(false)//这个是默认是回滚，不会commit入数据库，改成false 则commit
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    //注册测试
    @Test
    public void UserRegisterTest() {
        int last = userMapper.getUserList().size();

        userMapper.register(new User(1, "sonui", "123456", "Sonui", "test1@qq.com", "123abc", new Date(), 1));
        userMapper.register(new User(2, "test1", "123456", "张三", "test1@gmail.com", "123abc", new Date(), 1));
        userMapper.register(new User(3, "test2", "123456", "Lisi", "ls@baidu.com", "123abc", new Date(), 1));

        Assert.assertEquals(3, userMapper.getUserList().size() - last);
    }

    //使用实体登录测试
    @Test
    public void UserCheckLoginTest() {
        User res = userMapper.checkLoginByModel(new User("sonui", "123456"));
        Assert.assertEquals("sonui", res.getUserName());
        res = userMapper.checkLoginByModel(new User("sonui", "abc"));
        Assert.assertNull(res);
    }

    //使用key登录测试
    @Test
    public void UserCheckLoginTest2() {
        User res;
        res = userMapper.checkLoginByKey("sonui", "123456");
        Assert.assertEquals("sonui", res.getUserName());
        res = userMapper.checkLoginByKey("sonui", "abc");
        Assert.assertNull(res);
    }

    //更新用户信息测试
    @Test
    public void UserUpdateUserTest() {
        User res = new User("sonui", "123456");
        res = userMapper.checkLoginByModel(res);
        long uid = res.getUid();
        res.setNickName("sonui2");
        int rw = userMapper.updateUser(res);
        Assert.assertEquals(1, rw);
        res = userMapper.getUserInfo(uid);
        Assert.assertEquals("sonui2", res.getNickName());
    }

    //查询用户是否存在测试
    @Test
    public void UserCheckUserName() {
        int res = userMapper.checkUserName("sonui");
        Assert.assertEquals(1, res);
    }
}
