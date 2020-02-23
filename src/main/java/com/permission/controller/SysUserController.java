package com.permission.controller;


import com.permission.annotation.NoPermission;
import com.permission.annotation.Permission;
import com.permission.common.Result;
import com.permission.dto.input.SysUserLoginInput;
import com.permission.dto.input.SysUserRegisterInput;
import com.permission.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 添加用户
     * @param sysUserRegisterInput
     * @return
     */
    @PostMapping("/addUser")
    public Result register (@RequestBody SysUserRegisterInput sysUserRegisterInput) {
        return Result.success(sysUserService.addUser(sysUserRegisterInput));
    }

    /**
     * 用户登录
     * @param response
     * @param sysUserLoginInput
     * @return
     */
    @NoPermission
    @GetMapping("/login")
    public Result login (HttpServletResponse response, SysUserLoginInput sysUserLoginInput) {
        return Result.success(sysUserService.login(response, sysUserLoginInput));
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
