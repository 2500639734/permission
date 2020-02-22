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
    PASSWORD_ERROR (10005, "请检查密码是否正确"),
    PASSWORD_ENCRYPTION_FAIL (10006, "密码加密失败"),
    REGISTER_FAIL (10007, "注册失败"),
    PHONE_REGISTER (10008, "手机号已被注册");

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
