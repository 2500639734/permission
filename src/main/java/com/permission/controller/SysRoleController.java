package com.permission.controller;


import com.permission.annotation.CasUser;
import com.permission.annotation.Permission;
import com.permission.common.Result;
import com.permission.dto.input.sysrole.AddSysRoleInput;
import com.permission.dto.input.sysrole.SelectRoleInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@RestController
@RequestMapping("/sys-role")
// @Permission
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色列表
     * @return
     */
    @PostMapping("/selectRoleList")
    public Result selectRoleList (@RequestBody SelectRoleInput selectRoleInput) {
        return Result.success(sysRoleService.selectRoleList(selectRoleInput));
    }

    /**
     * 添加角色
     * @param sysUserInfo 当前登录用户
     * @param addSysRoleInput 添加角色入参
     * @return
     */
    @PostMapping("/addRole")
    public Result addRole (@CasUser SysUserInfo sysUserInfo, AddSysRoleInput addSysRoleInput) {
        return Result.success(sysRoleService.addRole(sysUserInfo, addSysRoleInput));
    }

}
