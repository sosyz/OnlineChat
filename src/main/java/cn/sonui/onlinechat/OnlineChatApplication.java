package cn.sonui.onlinechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.sonui.onlinechat.mapper")
public class OnlineChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineChatApplication.class, args);
    }

}
