package com.permission.util;

import cn.hutool.core.collection.CollectionUtil;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.exception.ParamValidException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 校验对象是否不为空
     * @param obj
     * @param resultEnum
     */
    public static void objectIsNotNuLL (Object obj, ResultEnum resultEnum) {
        if (obj != null) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验字符串是否为空
     * @param strs
     * @param resultEnum
     */
    public static void strIsNull (String strs, ResultEnum resultEnum) {
        if (StringUtils.isEmpty(strs)) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验字符串是否满足正则表达式
     * @param str
     * @param resultEnum
     * @return
     */
    public static void strIsMatchRegex (String str, String regex, ResultEnum resultEnum) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(regex)) {
            throw new BusinessException(resultEnum);
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (! matcher.matches()) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验字符串是否为空
     * StringUtils.isEmpty(str1) or StringUtils.isEmpty(str2) or ...StringUtils.isEmpty(strN)
     * 若全部为空，则抛出BusinessException
     * @param resultEnum
     * @param strs
     */
    public static void strIsNullOr (ResultEnum resultEnum, String ... strs) {
        int numbers = 0;
        for (int i = 0; i < strs.length; i++) {
            if (StringUtils.isEmpty(strs[i])) {
                numbers ++;
            }
        }

        if (numbers == strs.length) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验集合是否为空
     * @param collection
     * @param resultEnum
     * @param <E>
     */
    public static <E> void collectionIsNull (Collection<E> collection, ResultEnum resultEnum) {
        if (collection == null || collection.size() <= 0) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验数组是否为空
     * @param objects
     * @return
     */
    public static boolean isEmpty (Object[] objects) {
        return objects == null || objects.length <= 0;
    }

    /**
     * 校验数组是否不为空
     * @param objects
     * @return
     */
    public static boolean isNotEmpty (Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * 校验Boolean值是否为true
     * @param flag
     * @param resultEnum
     */
    public static void isTrue (Boolean flag, ResultEnum resultEnum) {
        if (flag == null || ! flag) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验Boolean值是否为false
     * @param flag
     * @param resultEnum
     */
    public static void isFalse (Boolean flag, ResultEnum resultEnum) {
        if (flag == null || flag) {
            throw new BusinessException(resultEnum);
        }
    }

}
