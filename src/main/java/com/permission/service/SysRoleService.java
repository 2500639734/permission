package com.permission.service;

import com.permission.dto.input.sysrole.AddSysRoleInput;
import com.permission.dto.input.sysrole.SelectRoleInput;
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
     * @param selectRoleInput 查询角色列表入参
     * @return
     */
    List<SysRole> selectRoleList (SelectRoleInput selectRoleInput);

    /**
     * 角色ids查询角色列表
     * @param ids
     * @return
     */
    List<SysRole> selectRoleListByIds (Collection<Integer> ids);

    /**
     * 用户id查询角色列表
     * @param userId
     * @return
     */
    List<SysRole> selectRoleListByUserId (Integer userId);

    /**
     * 根据角色编码查询角色
     * @param code
     * @return
     */
    SysRole selectRoleByCode (String code);

    /**
     * 添加角色
     * @param sysUserInfo 当前登录用户信息
     * @param sysRoleInput 添加角色入参
     * @return
     */
    SysRole addRole(SysUserInfo sysUserInfo, AddSysRoleInput sysRoleInput);

}
