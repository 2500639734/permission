package com.permission.exception;

import com.permission.enumeration.ResultEnum;
import lombok.Data;

import java.text.MessageFormat;

/**
 * @auther: shenke
 * @date: 2019/10/29 21:24
 * @description: 业务异常类,业务出错应抛出异常,由SpringExceptionResolver类统一处理
 */
@Data
public class BusinessException extends RuntimeException {

    /**
     * 返回结果枚举
     */
    private ResultEnum resultEnum;

    /**
     * 抛出业务异常,应指定具体的ResultEnum,抛出错误消息以便统一处理
     * @param resultEnum
     */
    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }

    /**
     * 抛出业务异常,应指定具体的ResultEnum,抛出错误消息以便统一处理
     * @param resultEnum
     * @param throwable
     */
    public BusinessException(ResultEnum resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.resultEnum = resultEnum;
    }

}
