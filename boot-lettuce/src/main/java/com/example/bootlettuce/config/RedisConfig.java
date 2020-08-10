package com.example.bootlettuce.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author yuxiangzang
 * @create 2020-07-06-5:51 下午
 * @desc Lettuce配置类
 **/
@Configuration
public class RedisConfig {

  /**
   * 覆盖了RedisAutoConfiguration里面的redisTemplate方法
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
  {
    RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(redisConnectionFactory);

    //如果不加下面这些配置，直接返回template
    //那么正常保存数据到redis时，redisTemplate会进行序列化，
    //默认使用到是JdkSerializationRedisSerializer进行数据序列化
    //所有到Key和value还有hashKey和hashValue到原始字符前，都加了一串字符
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);

    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    //在使用注解@Bean返回RedisTemplate的时候，同时配置hashKey和hashValue的序列化方法
    //key采用String的序列化方式
    template.setKeySerializer(stringRedisSerializer);
    //value采用序列化方式采用jackson
    template.setValueSerializer(jackson2JsonRedisSerializer);
    //hash的Key也采用String的序列化方式
    template.setHashKeySerializer(stringRedisSerializer);
    //hash的value序列化方式采用jackson
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }
}
