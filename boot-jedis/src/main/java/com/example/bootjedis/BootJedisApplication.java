package com.example.bootjedis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.bootjedis.mapper")
public class BootJedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootJedisApplication.class, args);
    }

}
