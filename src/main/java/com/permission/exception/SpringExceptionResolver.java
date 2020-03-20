package com.permission.exception;

import com.permission.common.Result;
import com.permission.enumeration.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @auther: shenke
 * @date: 2019/10/27 20:55
 * @description: Spring全局异常统一处理,用于产生异常时响应json
 */
@RestControllerAdvice
@Slf4j
public class SpringExceptionResolver {

    /**
     * 统一异常处理,主要处理参数错误,统一响应json
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException (HttpMessageNotReadableException e) {
        log.error("[请求异常]", e);
        return Result.build(ResultEnum.PARAM_ERROR);
    }

    /**
     * 统一异常处理,主要处理业务异常,统一响应json
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler (BusinessException e) {
        log.error("[请求异常]", e);
        return Result.build(e.getResultEnum());
    }

    /**
     * 统一异常处理,未发生以上异常的后置处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler (Exception e) {
        log.error("[请求异常]", e);
        return Result.error();
    }

}
