package com.permission.aspect;

import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.service.SysAclService;
import com.permission.util.SpringContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther: shenke
 * @date: 2020/2/21 20:25
 * @description: 系统权限切面
 */
@Aspect
@Component
public class SysPermissionsAspect {

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
        // TODO 用户id暂时写死
        if (! sysAclService.isPermission(1, SpringContextUtils.getCurrentRequest())) {
            throw new BusinessException(ResultEnum.NOT_PERMISSION, 1);
        }
    }

}
