package com.permission.util;

import cn.hutool.core.collection.CollectionUtil;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.exception.ParamValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

/**
 * @auther: shenke
 * @date: 2019/10/31 20:17
 * @description: 验证工具类
 */
public class ValidatedUtils {

    private ValidatedUtils () {

    }

    /**
     * 参数校验,出错时抛出异常,默认取第一个作为异常提示消息抛出
     * @param bindingResult
     */
    public static void paramValid (BindingResult bindingResult) {
        List<ObjectError> list = bindingResult.getAllErrors();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(objectError -> {
                throw new ParamValidException(objectError.getDefaultMessage());
            });
        }
    }

    /**
     * 获取参数校验异常结果中第一个错误提示
     * @param bindingResult
     * @return
     */
    public static String getParamExceptionFirstErrorMsg (BindingResult bindingResult) {
        List<ObjectError> objectErrorList = bindingResult.getAllErrors();
        if (CollectionUtil.isNotEmpty(objectErrorList)) {
            return objectErrorList.iterator().next().getDefaultMessage();
        }
        return null;
    }

    /**
     * 获取参数校验异常结果中第一个错误提示
     * @return
     */
    public static String getParamExceptionFirstErrorMsg (Set<ConstraintViolation<?>> constraintViolations) {
        if (CollectionUtil.isNotEmpty(constraintViolations)) {
            return constraintViolations.iterator().next().getMessage();
        }
        return null;
    }

    /**
     * 校验对象是否为空
     * @param obj
     * @param resultEnum
     */
    public static void objectIsNuLL (Object obj, ResultEnum resultEnum) {
        if (obj == null) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验对象是否为空
     * @param obj
     * @param resultEnum
     * @param msgs
     */
    public static void objectIsNuLL (Object obj, ResultEnum resultEnum, Object ... msgs) {
        if (obj == null) {
            throw new BusinessException(resultEnum, msgs);
        }
    }

}
