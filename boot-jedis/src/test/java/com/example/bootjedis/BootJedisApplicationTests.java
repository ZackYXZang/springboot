package com.example.bootjedis;

import com.example.bootjedis.config.JedisConfig;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class BootJedisApplicationTests {

    @Autowired
    private JedisPool jedisPool;


    @Test
    void contextLoads() {
        List<String> list = new ArrayList<>();
        list.add("1");
        System.out.println(list.get(1));
    }

}
