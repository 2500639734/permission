package com.permission.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther: shenke
 * @date: 2019/11/2 23:35
 * @description: 服务访问配置,允许跨域访问
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        // 添加CORS配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 放行哪些原始域
        corsConfiguration.addAllowedOrigin("*");
        // 是否发送cookie信息
        corsConfiguration.setAllowCredentials(false);
        // 放行哪些原始域（请求方式）
        corsConfiguration.addAllowedMethod("*");
        // 放行哪些原始域（头部信息）
        corsConfiguration.addAllowedHeader("*");
        // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        corsConfiguration.addExposedHeader(HttpHeaders.ACCEPT);

        // 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);

        // 返回新的CorsFileter
        return new CorsFilter(source);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOrigins("*")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET","POST", "PUT", "DELETE")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("Header1", "Header2");
            }
        };
    }

}