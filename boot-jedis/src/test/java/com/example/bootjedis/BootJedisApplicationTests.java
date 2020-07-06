package com.example.bootjedis;

import com.example.bootjedis.config.JedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootJedisApplicationTests {

    @Autowired
    private JedisConfig jedisConfig;

    @Test
    void contextLoads() {
        System.out.println(jedisConfig);
    }

}
