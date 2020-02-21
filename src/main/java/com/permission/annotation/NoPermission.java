package com.permission.annotation;

import java.lang.annotation.*;

/**
 * @auther: shenke
 * @date: 2020/2/22 7:04
 * @description: 不校验权限注解
 * 仅在@Permission标注了类或方法时才能生效
 * 被标注的方法不作权限校验
 * 不校验权限注解@NoPermission优先级高于@Permission
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoPermission {
}
