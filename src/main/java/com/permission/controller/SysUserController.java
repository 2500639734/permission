package com.permission.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.annotation.CasUser;
import com.permission.annotation.NoPermission;
import com.permission.annotation.Permission;
import com.permission.common.Result;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.input.sysuser.SysUserLoginInput;
import com.permission.dto.input.sysuser.SysUserInput;
import com.permission.pojo.SysUser;
import com.permission.service.SysUserService;
import com.permission.util.CookieUtils;
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
// @Permission
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/selectSysUserList")
    public Result selectSysUserList (@RequestBody SysUserInput sysUserInput) {
        IPage<SysUser> sysUserIPage = sysUserService.selectSysUserList(sysUserInput);
        return Result.success(sysUserIPage.getRecords(), sysUserIPage.getTotal());
    }

    /**
     * 添加用户
     * @param sysUserInfo 当前登录的用户信息
     * @param sysUserInput 添加用户入参
     * @return
     */
    @PostMapping("/addUser")
    public Result addUser (@CasUser SysUserInfo sysUserInfo, @RequestBody SysUserInput sysUserInput) {
        return Result.success(sysUserService.addUser(sysUserInfo, sysUserInput));
    }

    /**
     * 更新用户
     * @param sysUserInfo 当前登录的用户信息
     * @param sysUserInput 添加用户入参
     * @return
     */
    @PostMapping("/updateUser")
    public Result updateUser (@CasUser SysUserInfo sysUserInfo, @RequestBody SysUserInput sysUserInput) {
        return Result.success(sysUserService.updateUser(sysUserInfo, sysUserInput));
    }

    /**
     * 删除用户
     * @param userId 用户id
     * @return
     */
    @PostMapping("/deleteUser/{userId}")
    public Result deleteUser (@PathVariable("userId") Integer userId) {
        return Result.success(sysUserService.deleteUser(userId));
    }

    /**
     * 用户登录
     * @param response
     * @param sysUserLoginInput
     * @return
     */
    @NoPermission
    @PostMapping("/login")
    public Result login (HttpServletResponse response, @RequestBody SysUserLoginInput sysUserLoginInput) {
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

    /**
     * 获取当前登录的用户信息
     * @param request
     * @return
     */
    @PostMapping("/getCurrentCasUserInfo")
    public Result getCurrentCasUserInfo(HttpServletRequest request) {
        return Result.success(CookieUtils.currentCasUserInfo(request));
    }

}
