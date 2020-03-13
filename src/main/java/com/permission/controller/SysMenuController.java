package com.permission.controller;


import com.permission.common.Result;
import com.permission.enumeration.ResultEnum;
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
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/selectMenuList/{roleId}")
    public Result selectAclList (@PathVariable("roleId") Integer roleId) {
        return Result.build(ResultEnum.SUCCESS, sysMenuService.selectMenuTreeByRoleId(roleId));
    }

}
