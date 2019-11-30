package com.permission.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @auther: shenke
 * @date: 2019/10/27 21:10
 * @description: Spring上下文工具类
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    // Spring上下文对象
    private static ApplicationContext applicationContext;

    /**
     * 注入Spring上下文对象
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 根据class类型获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean (Class<T> clazz) {
        return clazz == null ? null : SpringContextUtils.applicationContext.getBean(clazz);
    }

    /**
     * 根据class类型和beanName获取bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean (String name, Class<T> clazz) {
        return clazz == null ? null : SpringContextUtils.applicationContext.getBean(name, clazz);
    }

}
