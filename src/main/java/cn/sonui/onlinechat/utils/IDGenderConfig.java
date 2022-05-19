package cn.sonui.onlinechat.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IDGenderConfig {

    //数据中心[0,31]
    @Value("${snowflake.datacenterId}")
    private long datacenterId;

    //机器标识[0,31]
    @Value("${snowflake.machineId}")
    private long machineId;

    @Bean
    public SnowFlakeFactory getSnowFlakeFactory() {
        SnowFlakeFactory snowFlakeFactory = new SnowFlakeFactory(datacenterId, machineId);
        return snowFlakeFactory;
    }
}