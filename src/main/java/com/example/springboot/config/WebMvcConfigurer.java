package com.example.springboot.config;

import com.example.springboot.Controller.interceptor.OneInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         *  * 拦截器按照顺序执行
         */
        registry.addInterceptor(new OneInterceptor()).addPathPatterns("/one/**");
        super.addInterceptors(registry);
    }
}
