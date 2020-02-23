package com.permission.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther: shenke
 * @date: 2019/10/27 20:49
 * @description: 统一返回JSON的枚举
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    /**
     * 系统相关枚举
     */
    SUCCESS (1000, "请求成功"),
    ERROR (1001, "服务器错误"),
    PARAM_ERROR (1002, "参数缺失"),
    MD5_ENCRYPTION_FAIL (1003, "MD5加密失败"),
    REDIS_OPERATION_FAIL (1004, "Redis操作失败"),

    /**
     * 安全相关校验枚举
     */
    NOT_LOGIN (2001, "您还未登录,请先登录"),
    NOT_PERMISSION (2002, "对不起,您没有访问权限"),

    /**
     * 用户相关枚举
     */
    NAME_IS_NULL (10001, "请填写姓名"),
    NAME__NOT_REGEX (10001, "请检查姓名是否正确"),
    USERNAME_IS_NULL (10001, "请填写用户名"),
    USERNAME_NOT_REGEX (1002, "请检查用户名是否正确"),
    PASSWORD_IS_NULL (10003, "请填写密码"),
    USER_NOT_EXISTS (10004, "用户不存在"),
    USER_EXISTS (10005, "用户已存在"),
    USERNAME_OR_PASSWORD_ERROR (10006, "用户名或密码错误"),
    PASSWORD_ENCRYPTION_FAIL (10007, "密码加密失败"),
    ADD_USER_FAIL (10008, "添加用户失败"),
    UPDATE_USER_FAIL (10009, "更新用户失败"),
    DELETE_USER_FAIL (10010, "删除用户失败"),
    USERNAME_EXISTS (10011, "用户名已存在"),

    /**
     * 角色相关枚举
     */
    ROLE_NAME_IS_NULL(11001, "请填写角色名称"),
    ROLE_NAME_NOT_REGEX(11002, "请检查角色名称是否正确"),
    ROLE_EXISTS (11004, "角色已存在"),
    ROLE_NOT_EXISTS (11005, "角色不存在"),
    ADD_ROLE_FAIL (11006, "添加角色失败"),
    NO_SELECTD_ROLE (11007, "请选择对应角色");

    /**
     * 状态码,必须唯一
     */
    private Integer code;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 根据状态码获取对应的ResultEnum
     * @param code
     * @return
     */
    public static ResultEnum getByCode (Integer code) {
        for (ResultEnum resultEnum: ResultEnum.values()) {
            if (resultEnum.code.equals(code)) {
                return resultEnum;
            }
        }
        return null;
    }

}
