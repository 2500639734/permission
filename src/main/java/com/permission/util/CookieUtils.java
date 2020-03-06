package com.permission.util;

import com.permission.dto.input.sysuser.CasUserInfo;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
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
        Cookie[] cookies = request.getCookies();
        if (ValidatedUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : request.getCookies()) {
                if (EncryptionUtils.LOGIIN_TOKEN_KEY.equals(cookie.getName())) {
                    return cookie.getValue();
                }
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
        // 设置应用域(文件路径)
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取当前登录用户信息
     * Cookie中获取LoginToken
     * 依据LoginToken获取登录用户信息
     * @param request
     * @return
     */
    public static CasUserInfo currentCasUserInfo (HttpServletRequest request) {
        String loginToken = getLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }

        CasUserInfo casUserInfo = (CasUserInfo) RedisUtils.get(loginToken);
        if (casUserInfo == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }

        return casUserInfo;
    }

}
