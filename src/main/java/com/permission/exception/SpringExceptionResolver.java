package com.permission.exception;

import com.alibaba.fastjson.JSON;
import com.permission.common.Result;
import com.permission.enumeration.ResultEnum;
import com.permission.util.ValidatedUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @auther: shenke
 * @date: 2019/10/27 20:55
 * @description: Spring全局异常统一处理,用于产生异常时响应json
 */
@RestControllerAdvice
@Slf4j
public class SpringExceptionResolver {

    /**
     * 参数校验异常处理,自动处理BindResult
     * @param e
     * @return
     */
    @ExceptionHandler(ParamValidException.class)
    public Result methodArgumentNotValidExceptionHandler (ParamValidException e) {
        log.error("[参数校验异常]", e);

        ResultEnum resultEnum = ResultEnum.buildExceptionResultEnum(ResultEnum.PARAM_ERROR, e.getMessage());
        return Result.build(resultEnum);
    }

    /**
     * 参数校验异常处理,自动处理BindResult
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException (MethodArgumentNotValidException e) {
        log.error("[参数校验异常]", e);

        String errorMsg = ValidatedUtils.getParamExceptionFirstErrorMsg(e.getBindingResult());
        ResultEnum resultEnum = ResultEnum.buildExceptionResultEnum(ResultEnum.PARAM_ERROR, errorMsg);
        return Result.build(resultEnum);
    }

    /**
     * 参数校验异常处理,自动处理BindResult
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler (ConstraintViolationException e) {
        log.error("[参数校验异常]", e);

        String errorMsg = ValidatedUtils.getParamExceptionFirstErrorMsg(e.getConstraintViolations());
        ResultEnum resultEnum = ResultEnum.buildExceptionResultEnum(ResultEnum.PARAM_ERROR, errorMsg);
        return Result.build(resultEnum);
    }

    /**
     * 参数校验异常处理,自动处理BindResult
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler (BindException e) {
        log.error("[参数校验异常]", e);

        String errorMsg = ValidatedUtils.getParamExceptionFirstErrorMsg(e.getBindingResult());
        ResultEnum resultEnum = ResultEnum.buildExceptionResultEnum(ResultEnum.PARAM_ERROR, errorMsg);
        return Result.build(resultEnum);
    }

    /**
     * 统一异常处理,主要处理业务异常,统一响应json
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler (BusinessException e) {
        log.error("[业务异常]", e);

        String message = e.getMessage();
        return JSON.parseObject(message, Result.class);
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
