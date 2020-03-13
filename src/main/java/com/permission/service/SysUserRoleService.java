package com.permission.service;

import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.input.sysuser.UserAuthorizationInput;
import com.permission.pojo.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 查询用户已存在的用户角色关联关系
     * @param userId
     * @return
     */
    List<SysUserRole> selectSysUserRoleByUserId (Integer userId);

    /**
     * 添加用户角色关系
     * @param sysUserInfo 当前登录用户信息
     * @param userAuthorizationInput 用户授权角色入参
     * @return
     */
    boolean addUserRoles(SysUserInfo sysUserInfo, UserAuthorizationInput userAuthorizationInput);

    /**
     * 删除用户角色关系
     * @param sysUserInfo 当前登录用户信息
     * @param userAuthorizationInput 取消用户授权角色入参
     * @return
     */
    boolean deleteUserRoles(SysUserInfo sysUserInfo, UserAuthorizationInput userAuthorizationInput);

    /**
     * 删除用户关联的角色
     * @param userId 用户id
     * @return
     */
    int deleteUserRoles(Integer userId);

}
