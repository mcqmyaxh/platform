package com.example.yuanqu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@ComponentScan(basePackages = {
        "com.example",                   // 你自己的代码

})
@ConfigurationPropertiesScan
@Import({RocketMQAutoConfiguration.class})
@EnableCaching
@EnableAsync
public class YuanquApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuanquApplication.class, args);
    }
}
