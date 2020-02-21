package com.permission.annotation;

import java.lang.annotation.*;

/**
 * @auther: shenke
 * @date: 2020/2/21 20:30
 * @description: 权限注解
 *  声明在类上：该类的所有接口将被校验接口权限
 *  声明在接口方法上：该接口方法将被校验接口权限
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

}
