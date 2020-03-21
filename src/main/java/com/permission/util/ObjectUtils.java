package com.permission.util;

import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther: shenke
 * @date: 2019/10/31 20:17
 * @description: 验证工具类
 */
public class ObjectUtils {

    private ObjectUtils() {

    }

    /**
     * 校验对象是否为空
     * @param obj
     * @param resultEnum
     */
    public static void isNull (Object obj, ResultEnum resultEnum) {
        if (obj == null) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验多个对象是否为空
     * @param resultEnum
     * @param objs
     */
    public static void isNull (ResultEnum resultEnum, Object ... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new BusinessException(resultEnum);
            }
        }
    }

    /**
     * 校验对象是否不为空
     * @param obj
     * @param resultEnum
     */
    public static void isNotNull (Object obj, ResultEnum resultEnum) {
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
        if (flag == null || flag == true) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 校验Boolean值是否为false
     * @param flag
     * @param resultEnum
     */
    public static void isFalse (Boolean flag, ResultEnum resultEnum) {
        if (flag == null || flag == false) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 指定的对象是否包含在对象列表中
     * @param resultEnum
     * @param obj
     * @param objs
     */
    public static void isContains (ResultEnum resultEnum, Object obj, Object ... objs) {
        boolean contains = false;
        for (Object o : objs) {
            if (o.equals(obj)) {
                contains = true;
            }
        }

        if(! contains) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * 指定对象是否包含在集合中
     * @param obj
     * @param collection
     * @param resultEnum
     */
    public static void isContains (Object obj, Collection collection, ResultEnum resultEnum) {
        if(collection.contains(obj)) {
            throw new BusinessException(resultEnum);
        }
    }

    /**
     * int值是否在指定区间
     * @param number int值
     * @param max 最大值
     * @param min 最小值
     * @param resultEnum
     */
    public static void intIsScope (Integer number, Integer max, Integer min, ResultEnum resultEnum) {
        if (number >= max || number <= min) {
            throw new BusinessException(resultEnum);
        }
    }

}
