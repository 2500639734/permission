package com.permission.aspect;

import com.permission.annotation.NoPermission;
import com.permission.dto.input.SysUserInfo;
import com.permission.dto.input.SysUserLoginOutput;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.service.SysAclService;
import com.permission.util.CookieUtils;
import com.permission.util.RedisUtils;
import com.permission.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @auther: shenke
 * @date: 2020/2/21 20:25
 * @description: 系统权限切面
 */
@Aspect
@Component
public class PermissionsAspect {

    @Autowired
    private SysAclService sysAclService;

    /**
     * 定义切入点，标注@Permission注解的类或方法将被切入
     */
    @Pointcut(value = "@within(com.permission.annotation.Permission)")
    public void point(){
    }

    /**
     * 前置通知，方法执行之前将被调用
     * 先检验是否已登录
     * 再检验是否拥有对应的接口权限
     * @param joinPoint
     */
    @Before(value = "point()")
    public void doBefore(JoinPoint joinPoint){
        // 当前方法标注了@NoPermission注解则不进行权限校验
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(NoPermission.class)) {
            return;
        }

        // 检验权限之前先检验是否已登录
        SysUserLoginOutput sysUserLoginOutput = checkLogin();

        // 校验是否有请求URI接口的访问权限
        if (! sysAclService.isPermission(sysUserLoginOutput.getSysUserInfo().getId(), SpringContextUtils.getCurrentRequest())) {
            throw new BusinessException(ResultEnum.NOT_PERMISSION);
        }
    }

    /**
     * 检查当前用户是否已登录
     * @return
     */
    private SysUserLoginOutput checkLogin(){
        HttpServletRequest currentRequest = SpringContextUtils.getCurrentRequest();

        // Cookie中获取当前登录用户的Token
        String loginToken = CookieUtils.getLoginToken(currentRequest);

        if (StringUtils.isEmpty(loginToken)) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }

        // Redis中获取当前登录的用户信息
        SysUserLoginOutput sysUserLoginOutput = (SysUserLoginOutput) RedisUtils.get(loginToken);
        if (sysUserLoginOutput == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }

        return sysUserLoginOutput;
    }

}
