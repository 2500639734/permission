package com.permission.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther: shenke
 * @date: 2020/2/22 8:55
 * @description: 正则表达式枚举类
 */
@AllArgsConstructor
@Getter
public enum RegexEnum {

    NAME_MAX_LENGTH (10, "用户姓名最大长度校验", "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*"),
    USERNAME_MAX_LENGTH(20, "用户名最大长度正则表达式", "^.{6,16}$");

    /**
     * 唯一编码
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 正则表达式
     */
    private String regex;

}
