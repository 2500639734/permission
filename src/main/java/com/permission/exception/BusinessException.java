package com.permission.exception;

import com.permission.enumeration.ResultEnum;

import java.text.MessageFormat;

/**
 * @auther: shenke
 * @date: 2019/10/29 21:24
 * @description: 业务异常类,业务出错应抛出异常,由SpringExceptionResolver类统一处理
 */
public class BusinessException extends RuntimeException {

    /**
     * 抛出业务异常,应指定具体的ResultEnum,抛出错误消息以便统一处理
     * @param resultEnum
     */
    public BusinessException(ResultEnum resultEnum) {
        super(ResultEnum.buildExceptionMsg(resultEnum));
    }

    /**
     * 抛出业务异常,应指定具体的ResultEnum,抛出错误消息以便统一处理
     * @param resultEnum
     * @param msgs
     */
    public BusinessException(ResultEnum resultEnum, Object ... msgs) {
        super(MessageFormat.format(ResultEnum.buildExceptionMsg(resultEnum), msgs));
    }

    /**
     * 抛出业务异常,应指定具体的ResultEnum,抛出错误消息以便统一处理
     * @param resultEnum
     * @param throwable
     */
    public BusinessException(ResultEnum resultEnum, Throwable throwable) {
        super(ResultEnum.buildExceptionMsg(resultEnum), throwable);
    }

    /**
     * 抛出业务异常,应指定具体的ResultEnum,抛出错误消息以便统一处理
     * @param resultEnum
     * @param throwable
     * @param msgs
     */
    public BusinessException(ResultEnum resultEnum, Throwable throwable, Object ... msgs) {
        super(MessageFormat.format(ResultEnum.buildExceptionMsg(resultEnum), msgs), throwable);
    }

}
