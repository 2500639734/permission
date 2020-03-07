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
    NOT_PERMISSION (2002, "对不起,您没有接口访问权限"),

    /**
     * 用户相关枚举
     */
    NAME_IS_NULL (10001, "请填写姓名"),
    NAME__NOT_REGEX (10001, "请检查姓名是否正确"),
    USERNAME_IS_NULL (10001, "请填写用户名"),
    USERNAME_NOT_REGEX (1002, "请检查用户名是否正确"),
    PASSWORD_IS_NULL (10003, "请填写密码"),
    PASSWORD_NOT_REGEX (10004, "请检查密码是否正确"),
    USER_NOT_EXISTS (10005, "用户不存在"),
    USER_EXISTS (10006, "用户已存在"),
    USERNAME_OR_PASSWORD_ERROR (10007, "用户名或密码错误"),
    PASSWORD_ENCRYPTION_FAIL (10008, "密码加密失败"),
    ADD_USER_FAIL (10009, "添加用户失败"),
    UPDATE_USER_FAIL (10010, "更新用户失败"),
    DELETE_USER_FAIL (10011, "删除用户失败"),
    USERNAME_EXISTS (10012, "用户名已存在"),

    /**
     * 角色相关枚举
     */
    ROLE_NAME_IS_NULL(11001, "请填写角色名称"),
    ROLE_NAME_NOT_REGEX(11002, "请检查角色名称是否正确"),
    ROLE_EXISTS (11004, "角色已存在"),
    ROLE_NOT_EXISTS (11005, "角色不存在"),
    ADD_ROLE_FAIL (11006, "添加角色失败"),
    NO_SELECTD_ROLE (11007, "请选择对应角色"),
    ROLE_NAME_EXISTS (11008, "角色名称已存在"),
    DELETE_ROLE_FAIL (11009, "删除角色失败"),
    ROLE_AUTHORIZATION_FAIL (11010, "角色授权失败"),

    /**
     * 权限相关枚举
     */
    PARENT_ACL_NOT_EXISTS (12001, "父级权限不存在"),
    ACL_NAME_IS_NULL (12002, "请填写权限名称"),
    ACL_NAME_NOT_REGEX(12003, "请检查权限名称是否正确"),
    ACL_CODE_IS_NULL (12004, "请填写权限编码"),
    ACL_CODE_NOT_REGEX (12005, "请检查权限编码是否正确"),
    ACL_TYPE_IS_NULL (12006, "请选择权限类型"),
    ADD_ACL_FILA (12007, "添加权限失败"),
    UPDATE_ACL_FILA (12008, "更新权限失败"),
    DELETE_ACL_FILA (12009, "删除权限失败"),
    ACL_NOT_EXISTS (12010, "权限不存在"),
    ACL_NAME_EXISTS (12011, "权限名称已存在"),
    ACL_CODE_EXISTS (12012, "权限编码已存在"),
    ACL_EXISTS (12013, "权限不存在");




    /**
     * 状态码,必须唯一
     */
    private Integer code;

    /**
     * 提示消息
     */
    private String msg;

}
