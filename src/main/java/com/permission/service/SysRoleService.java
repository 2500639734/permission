package com.permission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.dto.SysRoleDto;
import com.permission.dto.input.sysrole.RoleAuthorizationInput;
import com.permission.dto.input.sysrole.SysRoleInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.pojo.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询角色列表
     * @param sysRoleInput 查询角色列表入参
     * @return
     */
    IPage<SysRoleDto> selectSysRoleList(SysRoleInput sysRoleInput);

    /**
     * 角色id查询角色
     * @param id 角色id
     * @return
     */
    SysRole selectRoleById(Integer id);

    /**
     * 角色ids查询角色列表
     * @param ids
     * @return
     */
    List<SysRole> selectRoleListByIds(Collection<Integer> ids);

    /**
     * 根据角色编码查询角色
     * @param code
     * @return
     */
    SysRole selectRoleByCode(String code);

    /**
     * 查询用户包含的角色列表
     * @param userId 用户id
     * @return
     */
    List<SysRole> selectSysRoleListByUserId(Integer userId);

    /**
     * 添加角色
     * @param sysUserInfo 当前登录用户信息
     * @param sysRoleInput 添加角色入参
     * @return
     */
    SysRole addRole(SysUserInfo sysUserInfo, SysRoleInput sysRoleInput);

    /**
     * 修改角色
     * @param sysUserInfo 当前登录用户信息
     * @param sysRoleInput 修改角色入参
     * @return
     */
    SysRole updateRole(SysUserInfo sysUserInfo, SysRoleInput sysRoleInput);

    /**
     * 删除角色
     * @param roleId 角色id
     * @return
     */
    int deleteRole(Integer roleId);

    /**
     * 角色授权 / 取消授权菜单
     * @param sysUserInfo 当前登录用户信息
     * @param roleAuthorizationInput 角色授权 / 取消授权入参
     * @return
     */
    boolean authorizationMenu(SysUserInfo sysUserInfo, RoleAuthorizationInput roleAuthorizationInput);

}
