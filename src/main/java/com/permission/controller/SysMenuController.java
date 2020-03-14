package com.permission.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.annotation.Permission;
import com.permission.annotation.RestFulPermission;
import com.permission.common.Result;
import com.permission.dto.SysMenuDto;
import com.permission.dto.SysMenuTree;
import com.permission.dto.input.sysmenu.SysMenuInput;
import com.permission.enumeration.ResultEnum;
import com.permission.pojo.SysMenu;
import com.permission.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/sys-menu")
@Permission
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 分页查询树形菜单列表
     * @param sysMenuInput
     * @return
     */
    @PostMapping("/selectSysMenuTreeList")
    public Result selectSysMenuTreeList (@RequestBody SysMenuInput sysMenuInput) {
        IPage<SysMenuTree> sysMenuIPage = sysMenuService.selectSysMenuTreeList(sysMenuInput);
        return Result.success(sysMenuIPage.getRecords(), sysMenuIPage.getTotal());
    }

    /**
     * 获取角色包含的菜单列表,树形结构
     * @param roleId 角色id
     * @return
     */
    @RestFulPermission(aclCode = "menu:selectMenuTreeByRoleId")
    @PostMapping("/selectMenuTreeByRoleId/{roleId}")
    public Result selectMenuTreeByRoleId (@PathVariable("roleId") Integer roleId) {
        return Result.build(ResultEnum.SUCCESS, sysMenuService.selectMenuTreeByRoleId(roleId));
    }

}
