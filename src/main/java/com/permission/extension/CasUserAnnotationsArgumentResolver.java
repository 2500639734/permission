package com.permission.extension;

import com.permission.annotation.CasUser;
import com.permission.dto.input.sysuser.CasUserInfo;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.util.CookieUtils;
import com.permission.util.EncryptionUtils;
import com.permission.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther: shenke
 * @date: 2020/2/22 6:37
 * @description: @CasUser注解参数解析器
 */
public class CasUserAnnotationsArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 标注了@CasUser注解的参数进行解析注入
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CasUser.class);
    }

    /**
     * 具体的参数注入方法
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 校验标注@CasUser注解的参数类型是否为SysUserInfo
        if (methodParameter.getParameterType().equals(SysUserInfo.class)) {

            // 获取当前登录用户信息注入到CasUser
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            CasUserInfo casUserInfo = CookieUtils.currentCasUserInfo(request);

            return casUserInfo == null ? null : casUserInfo.getSysUserInfo();
        }
        return null;
    }

}
