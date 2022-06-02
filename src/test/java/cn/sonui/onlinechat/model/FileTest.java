package cn.sonui.onlinechat.model;

import cn.sonui.onlinechat.mapper.FilesMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest    //缓存MybatisTest注解
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //这个是启用自己配置的数据元，不加则采用虚拟数据源
@Rollback(false)//这个是默认是回滚，不会commit入数据库，改成false 则commit
public class FileTest {
    @Autowired
    private FilesMapper fileMapper;

    //注册测试
    @Test
    public void InsertFileTest() {
        FileModel file = new FileModel();
        file.setName("test.txt");
        file.setPath("/test/test.txt");
        file.setSize(100L);
        file.setType(1);
        file.setId(UUID.randomUUID().toString());
        file.setCreateTime(new Date());


    }
}
