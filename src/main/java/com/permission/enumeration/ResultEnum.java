package com.permission.enumeration;

import com.alibaba.fastjson.JSON;
import com.permission.common.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

/**
 * @auther: shenke
 * @date: 2019/10/27 20:49
 * @description: 统一返回JSON的枚举
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    /**
     * 系统相关枚举,仅打印日志不必返回前端
     */
    SUCCESS (1000, "请求成功"),
    ERROR (1001, "服务器错误"),
    PARAM_ERROR (1002, "参数缺失:{0}"),
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
    LOGIN_FAIL_USERNAME_OR_PHONE_IS_NULL (10001, "登录失败,请填写用户名或手机号"),
    LOGIN_FAIL_PASSWORD_IS_NULL (10002, "登录失败,请填写密码"),
    LOGIN_FAIL_USER_NOT_EXISTS (10002, "登录失败,用户不存在"),
    LOGIN_FAIL_BY_PASSWORD_ERROR (10003, "登录失败,请检查密码是否正确"),
    LOGIN_USER_PASSWORD_ENCRYPTION_FAIL (10004, "用户密码加密失败");

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

    /**
     * 构建业务异常提示消息
     * code:{],msg:[]
     * @param resultEnum
     * @return
     */
    public static String buildExceptionMsg (ResultEnum resultEnum, Object ... msgs) {
        String formatMsg = MessageFormat.format(resultEnum.getMsg(), msgs);
        return JSON.toJSONString(Result.build(resultEnum.getCode(), formatMsg));
    }

    /**
     * 构建异常时的返回枚举
     * @param resultEnum
     * @param msgs
     * @return
     */
    public static ResultEnum buildExceptionResultEnum (ResultEnum resultEnum, Object ... msgs) {
        resultEnum.msg = MessageFormat.format(resultEnum.msg, msgs);
        return resultEnum;
    }

}
