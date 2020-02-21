package com.permission.annotation;

import java.lang.annotation.*;

/**
 * @auther: shenke
 * @date: 2020/2/21 20:30
 * @description: 权限注解
 *  声明于类：该类的所有接口将被校验权限
 *  声明于接口方法：该接口方法将被校验权限
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

}
