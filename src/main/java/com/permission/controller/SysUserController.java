package com.permission.controller;


import com.permission.common.Result;
import com.permission.dto.input.SysUserQueryInput;
import com.permission.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户列表
     * @return
     */
    @PostMapping("/selectUserList")
    @ResponseBody
    public Result selectUserList (@RequestBody SysUserQueryInput sysUserQueryInput) {
        return Result.success(sysUserService.selectUserList(sysUserQueryInput));
    }

    @PostMapping("/deleteSysUserList")
    @ResponseBody
    public Result deleteSysUserList (@RequestParam(value = "sysUserIdList[]") List<Long> sysUserIdList) {
        return Result.success(sysUserService.deleteSysUserList(sysUserIdList));
    }

}
