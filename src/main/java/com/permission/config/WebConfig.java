package com.permission.config;

import com.permission.extension.CasUserAnnotationsArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @auther: shenke
 * @date: 2019/10/27 21:40
 * @description: Web拦截器配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置URL请求的拦截策略
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置Http请求拦截器拦截所有请求
        // registry.addInterceptor(httpInterceptor).addPathPatterns("/**");
    }

    /**
     * 配置静态资源的拦截策略
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    /**
     * 配置自定义参数解析器
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CasUserAnnotationsArgumentResolver());
    }

}