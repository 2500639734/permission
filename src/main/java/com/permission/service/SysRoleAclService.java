package com.permission.service;

import com.permission.dto.input.sysrole.RoleAuthorizationInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.pojo.SysRoleAcl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限关系表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysRoleAclService extends IService<SysRoleAcl> {

    /**
     * 查询角色已存在的角色权限关联关系
     * @param roleId 角色id
     * @return
     */
    List<SysRoleAcl> selectSysRoleAclByRoleId (Integer roleId);

    /**
     * 角色授权权限
     * @param sysUserInfo 当前登录的用户信息
     * @param roleAuthorizationInput 角色授权权限入参
     * @return
     */
    boolean addRoleAcls(SysUserInfo sysUserInfo, RoleAuthorizationInput roleAuthorizationInput);

    /**
     * 取消角色授权的权限
     * @param sysUserInfo 当前登录的用户信息
     * @param roleAuthorizationInput 取消用户授权角色入参
     * @return
     */
    boolean deleteRoleAcls(SysUserInfo sysUserInfo, RoleAuthorizationInput roleAuthorizationInput);

}
