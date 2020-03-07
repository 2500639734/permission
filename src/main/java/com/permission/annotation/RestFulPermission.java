package com.permission.annotation;

import java.lang.annotation.*;

/**
 * @auther: shenke
 * @date: 2020/2/22 7:04
 * @description: RestFul接口权限注解
 * 仅在@Permission标注了类或方法时生效
 * 被标注的方法依据权限标识校验
 * 接口为RestFul风格且需要权限校验时应在方法上声明此接口并将aclCode和权限配置保持一致
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestFulPermission {

    /**
     * 权限标识
     */
    String aclCode();

}
