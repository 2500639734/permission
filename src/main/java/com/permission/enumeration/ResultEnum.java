package com.permission.enumeration;

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

    /** 请求枚举*/
    PARAM_ERROR (100, "参数错误:{0}"),
    SUCCESS (200, "请求成功"),
    ERROR (500, "服务器错误"),

    /*----------------------- 部门业务相关枚举Start 1001 -----------------------*/
    DEPT_NOT_EXISTS(1001, "当前部门不存在,部门id:{0}"),
    DEPT_NAME_SAME (1002, "同一层级下存在相同名称的部门,上级部门id:{0},部门名称:{1}"),
    /*----------------------- 部门业务相关枚举End 1099-----------------------*/

    /*----------------------- 用户业务相关枚举Start 1001 -----------------------*/
    USER_NOT_EXISTS(2001, "当前用户不存在,用户id:{0}");
    /*----------------------- 用户业务相关枚举End 1099-----------------------*/

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
    public static String buildExceptionMsg (ResultEnum resultEnum) {
        return new StringBuffer("code:(")
                .append(resultEnum.getCode())
                .append("),")
                .append("msg:(")
                .append(resultEnum.getMsg())
                .append(")")
                .toString();
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
