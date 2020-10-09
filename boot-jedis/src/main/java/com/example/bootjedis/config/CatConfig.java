package com.example.bootjedis.config;

import com.dianping.cat.servlet.CatFilter;
import javax.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author yuxiangzang
 * @create 2020-08-06-11:22 上午
 * @desc Cat配置类
 **/
@Profile({"prod", "dev"})
@Configuration
public class CatConfig {

  @Bean
  public FilterRegistrationBean catFilter() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new CatFilter());
    registrationBean.addUrlPatterns("/*");
    registrationBean.setName("cat-filter");
    registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
    registrationBean.setOrder(1);
    return registrationBean;
  }
}
