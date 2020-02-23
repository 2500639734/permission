package com.permission.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther: shenke
 * @date: 2020/2/23 9:15
 * @description: Cookie操作工具类
 */
public class CookieUtils {

    private CookieUtils () {

    }

    /**
     * 从Cookie中获取登录Token
     * @param request
     * @return
     */
    public static String getLoginToken (HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (EncryptionUtils.LOGIIN_TOKEN_KEY.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 设置登录Token到Cookie
     * @param response
     * @param token
     */
    public static void setLoginToke (HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return;
        }

        // 将token写入Cookie
        Cookie cookie = new Cookie(EncryptionUtils.LOGIIN_TOKEN_KEY, token);
        // 设置有效时间，单位秒
        cookie.setMaxAge(EncryptionUtils.LOGIIN_TOKEN_DEFAULT_TIME_OUT_SECONDS);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
