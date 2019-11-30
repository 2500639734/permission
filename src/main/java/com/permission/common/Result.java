package com.permission.common;

import com.permission.enumeration.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @auther: shenke
 * @date: 2019/10/27 20:47
 * @description: 统一JSON返回结果
 */
@Data
@AllArgsConstructor
public class Result {

    private Integer code;

    private String msg;

    private Object data;

    private Integer total;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回请求成功
     * @param data
     * @return
     */
    public static Result success (Object data) {
        return build(ResultEnum.SUCCESS, data);
    }

    /**
     * 返回请求成功无数据
     * @return
     */
    public static Result success () {
        return build(ResultEnum.SUCCESS);
    }

    /**
     * 返回请求失败
     * @param data
     * @return
     */
    public static Result error (Object data) {
        return build(ResultEnum.ERROR, data);
    }

    /**
     * 返回请求失败无数据
     * @return
     */
    public static Result error () {
        return build(ResultEnum.ERROR);
    }

    /**
     * 构建指定返回结果
     * @param resultEnum
     * @return
     */
    public static Result build (ResultEnum resultEnum) {
        return build(resultEnum, null);
    }

    /**
     * 构建指定返回结果
     * @param resultEnum
     * @param data
     * @return
     */
    public static Result build (ResultEnum resultEnum, Object data) {
        return new Result(resultEnum.getCode(), resultEnum.getMsg(), data, 6);
    }

    /**
     * 构建自定义返回结果,用于统一异常处理,见SpringExceptionResolver
     * 业务异常建议使用ResultEnum构建
     * @param code
     * @param msg
     * @return
     */
    public static Result build (Integer code, String msg) {
        return new Result(code, msg);
    }

}
