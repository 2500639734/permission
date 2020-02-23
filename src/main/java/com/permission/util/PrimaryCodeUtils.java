package com.permission.util;

import cn.hutool.core.date.DateUtil;
import com.permission.enumeration.PrimaryCodeEnum;

import java.util.Random;

/**
 * @auther: shenke
 * @date: 2020/2/22 9:07
 * @description: 创建唯一编码工具类
 */
public class PrimaryCodeUtils {

    /**
     * 默认生成唯一编码的随机数位数
     */
    private static final int PRIMARY_CODE_DIGITS = 6;

    /**
     * 唯一序列号key前缀
     */
    private static final String PRIMARY_CODE_KEY_PREFIX = "PRIMARY-";

    private PrimaryCodeUtils() {

    }

    /**
     * 创建唯一编码
     * 规则：唯一编码前缀 + "-" + "yyyyMMdd" + "-" + 6位随机数 + "-" + "唯一序列号"
     * @param primaryCodeEnum
     * @return
     */
    public static String createPrimaryCode (PrimaryCodeEnum primaryCodeEnum) {
        if (primaryCodeEnum == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder(primaryCodeEnum.name());
        stringBuilder.append("-").
                append(DateUtil.today().replace("-", "")).
                append("-").
                append(getRandom(PRIMARY_CODE_DIGITS)).
                append("-").
                append(createPrimaryNo(primaryCodeEnum));

        return stringBuilder.toString();
    }

    /**
     * 创建唯一自增序列
     * @param primaryCodeEnum
     * @return
     */
    public static long createPrimaryNo (PrimaryCodeEnum primaryCodeEnum) {
        return RedisUtils.getAndIncrement(getPrimaryCodeKey(primaryCodeEnum), DateUtils.getCurrent2TodayEndMillisTime());
    }

    /**
     * 生成指定位数的伪随机数
     * @param digits 位数
     * @return
     */
    public static String getRandom (int digits) {
        if (digits <= 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digits; i ++) {
            Random random = new Random();
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    /**
     * 获取唯一序列key
     * @param primaryCodeEnum
     * @return
     */
    public static String getPrimaryCodeKey (PrimaryCodeEnum primaryCodeEnum) {
        if (primaryCodeEnum == null) {
            return null;
        }

        return new StringBuilder(PRIMARY_CODE_KEY_PREFIX).append(primaryCodeEnum.name()).toString();
    }

}
