package com.permission.util;

import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @auther: shenke
 * @date: 2020/2/21 22:40
 * @description: 加密工具类
 */
public class EncryptionUtils {

    private EncryptionUtils () {

    }

    /**
     * 默认MD5加密次数
     */
    private static final Integer DEFAULT_MD5_TIMES = 5;

    /**
     * 用户登录Token默认超时时间2小时
     */
    public static final long LOGIIN_TOKEN_DEFAULT_TIME_OUT_MS = 1000 * 60 * 60 * 2;

    /**
     * 请求接口时header中携带的获取token的key
     */
    public static final String LOGIIN_TOKEN_KEY = "login_token";

    /**
     * 获取加密后的用户密码
     * 加密方式 = 用户密码与盐值拼接后MD5加密DEFAULT_MD5_TIMES次
     * @param password
     * @param passwordSalt
     * @return
     */
    public static String getPassword (String password, String passwordSalt) {
        if (StringUtils.isEmpty(password ) || StringUtils.isEmpty(passwordSalt)) {
            throw new BusinessException(ResultEnum.PASSWORD_ENCRYPTION_FAIL);
        }

        String oldPassword = new StringBuilder(password).append(passwordSalt).toString();
        for (int i = 0; i < DEFAULT_MD5_TIMES; i ++) {
            oldPassword = md5(oldPassword);
        }
        return oldPassword;
    }

    /**
     * 字符串进行MD5加密
     * @param str
     * @return
     */
    public static String md5 (String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            BASE64Encoder base64 = new BASE64Encoder();
            return base64.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new BusinessException(ResultEnum.MD5_ENCRYPTION_FAIL, e);
        }
    }

    /**
     * 获取16位的UUID
     * @return
     */
    public static String uuid () {
        return UUID.randomUUID().toString();
    }

}
