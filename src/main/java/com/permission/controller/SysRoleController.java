package com.permission.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.annotation.CasUser;
import com.permission.common.Result;
import com.permission.dto.input.sysrole.SysRoleInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.pojo.SysRole;
import com.permission.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result selectRoleList (@RequestBody SysRoleInput sysRoleInput) {
        IPage<SysRole> sysRoleIPage = sysRoleService.selectRoleList(sysRoleInput);
        return Result.success(sysRoleIPage.getRecords(), sysRoleIPage.getTotal());
    }

    /**
     * 添加角色
     * @param sysUserInfo 当前登录用户
     * @param sysRoleInput 添加角色入参
     * @return
     */
    @PostMapping("/addRole")
    public Result addRole (@CasUser SysUserInfo sysUserInfo, @RequestBody SysRoleInput sysRoleInput) {
        return Result.success(sysRoleService.addRole(sysUserInfo, sysRoleInput));
    }

    /**
     * 更新角色
     * @param sysUserInfo 当前登录用户
     * @param sysRoleInput 添加角色入参
     * @return
     */
    @PostMapping("/updateRole")
    public Result updateRole (@CasUser SysUserInfo sysUserInfo, @RequestBody SysRoleInput sysRoleInput) {
        return Result.success(sysRoleService.updateRole(sysUserInfo, sysRoleInput));
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return
     */
    @PostMapping("/deleteRole/{roleId}")
    public Result updateRole (@PathVariable("roleId") Integer roleId) {
        return Result.success(sysRoleService.deleteRole(roleId));
    }

}
