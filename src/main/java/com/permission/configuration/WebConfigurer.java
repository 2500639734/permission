package com.permission.configuration;

import com.permission.interceptor.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther: shenke
 * @date: 2019/10/27 21:40
 * @description: Web拦截器配置
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private HttpInterceptor httpInterceptor;

    /**
     * 配置URL请求的拦截策略
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置Http请求拦截器拦截所有请求
        registry.addInterceptor(httpInterceptor).addPathPatterns("/**");
    }

    /**
     * 配置静态资源的拦截策略
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
}
