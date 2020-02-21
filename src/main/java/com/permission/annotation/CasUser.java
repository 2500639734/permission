package com.permission.annotation;

import java.lang.annotation.*;

/**
 * @auther: shenke
 * @date: 2020/2/22 0:43
 * @description: 用户信息注解
 * 声明在参数上：自动注入当前登录用户信息到SysUserInfo
 * 若当前用户未登录,则返回null
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CasUser {
}
