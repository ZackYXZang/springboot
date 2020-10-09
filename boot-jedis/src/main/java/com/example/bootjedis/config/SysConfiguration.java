package com.example.bootjedis.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: liuchen
 * @date: 2020/7/20
 * @time: 2:48 PM
 * @Description:
 */
@Configuration
public class SysConfiguration {
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(7))
        .setReadTimeout(Duration.ofSeconds(7)).additionalCustomizers().build();
  }
}
