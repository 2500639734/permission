package com.permission.aspect;

import com.permission.annotation.NoPermission;
import com.permission.dto.input.SysUserInfo;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.service.SysAclService;
import com.permission.util.EncryptionUtils;
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

        HttpServletRequest currentRequest = SpringContextUtils.getCurrentRequest();

        // HttpServletRequest的请求头中获取Redis中存放的用户登录Token的Key
        String tokenKey = currentRequest.getHeader(EncryptionUtils.LOGIIN_TOKEN_KEY);
        if (StringUtils.isEmpty(tokenKey)) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }

        // Redis中获取当前登录的用户信息
        SysUserInfo sysUserInfo = (SysUserInfo) RedisUtils.get(tokenKey);
        if (sysUserInfo == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN);
        }

        // 校验是否有请求URI接口的访问权限
        if (! sysAclService.isPermission(sysUserInfo.getId(), SpringContextUtils.getCurrentRequest())) {
            throw new BusinessException(ResultEnum.NOT_PERMISSION, 1);
        }
    }

}
