package com.example.bootjedis;

import com.example.bootjedis.config.JedisConfig;
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
        Jedis jedis = jedisPool.getResource();
        Double zscore = jedis.zscore("a", "a");
        System.out.println(zscore);
    }

}
