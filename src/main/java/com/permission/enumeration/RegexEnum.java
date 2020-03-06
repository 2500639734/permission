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

    NAME (10, "用户姓名正则表达式(2-5位中文汉字)", "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*"),
    USERNAME(20, "用户名正则表达式(6-16位字符)", "^.{6,16}$"),
    PASSWORD(30, "密码正则表达式(6-16位字符)", "^.{6,16}$"),
    ROLE_NAME(40, "角色姓名正则表达式(2-8位中文汉字)", "[\u4E00-\u9FA5]{2,8}(?:·[\u4E00-\u9FA5]{2,8})*"),
    ACL_NAME (50, "权限名称正则表达式(2-8位中文汉字)", "[\u4E00-\u9FA5]{2,8}(?:·[\u4E00-\u9FA5]{2,5})*"),
    ACL_CODE (60, "权限编码正则表达式(2-16位字符)", "^.{2,16}$");

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
