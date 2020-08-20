package com.example.bootjedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootJedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootJedisApplication.class, args);
    }

}
