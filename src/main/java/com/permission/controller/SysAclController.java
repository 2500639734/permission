package com.permission.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.annotation.CasUser;
import com.permission.common.Result;
import com.permission.dto.input.sysacl.SysAclInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.output.SysAclOutput;
import com.permission.enumeration.ResultEnum;
import com.permission.pojo.SysAcl;
import com.permission.service.SysAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@RestController
@RequestMapping("/sys-acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    /**
     * 分页查询权限列表
     * @param sysAclInput
     * @return
     */
    @PostMapping("/selectAclList")
    public Result selectAclList (@RequestBody SysAclInput sysAclInput) {
        IPage<SysAclOutput> sysAclOutputIPage = sysAclService.selectAclList(sysAclInput);
        return Result.build(ResultEnum.SUCCESS, sysAclOutputIPage.getRecords(), sysAclOutputIPage.getTotal());
    }

    /**
     * 添加权限
     * @param sysUserInfo 当前登录用户信息
     * @param sysAclInput 添加权限入参
     * @return
     */
    @PostMapping("/addSysAcl")
    public Result addSysAcl (@CasUser SysUserInfo sysUserInfo, @RequestBody SysAclInput sysAclInput) {
        return Result.build(ResultEnum.SUCCESS, sysAclService.addSysAcl(sysUserInfo, sysAclInput));
    }

    /**
     * 更新权限
     * @param sysUserInfo 当前登录用户信息
     * @param sysAclInput 更新权限入参
     * @return
     */
    @PostMapping("/updateSysAcl")
    public Result updateSysAcl (@CasUser SysUserInfo sysUserInfo, @RequestBody SysAclInput sysAclInput) {
        return Result.build(ResultEnum.SUCCESS, sysAclService.updateSysAcl(sysUserInfo, sysAclInput));
    }

    /**
     * 删除权限
     * @param id 权限id
     * @return
     */
    @PostMapping("/deleteSysAcl/{id}")
    public Result updateSysAcl (@PathVariable("id") Integer id) {
        return Result.build(ResultEnum.SUCCESS, sysAclService.deleteSysAcl(id));
    }

}
