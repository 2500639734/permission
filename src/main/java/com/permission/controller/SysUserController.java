package com.permission.controller;


import com.permission.annotation.NoPermission;
import com.permission.annotation.Permission;
import com.permission.common.Result;
import com.permission.dto.input.SysUserLoginInput;
import com.permission.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@RestController
@RequestMapping("/sys-user")
@Permission
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     * @param sysUserLoginInput
     * @return
     */
    @NoPermission
    @PostMapping("/login")
    public Result login (@RequestBody SysUserLoginInput sysUserLoginInput) {
        return Result.success(sysUserService.login(sysUserLoginInput));
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result logout (HttpServletRequest request) {
        sysUserService.logout(request);
        return Result.success();
    }

}
