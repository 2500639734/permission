package com.permission.exception;

/**
 * @auther: shenke
 * @date: 2019/10/31 20:32
 * @description: 参数校验异常类,参数校验未通过时应抛出此异常
 */
public class ParamValidException extends RuntimeException {

    /**
     * 参数校验异常
     * @param message
     */
    public ParamValidException(String message) {
        super(message);
    }

}
