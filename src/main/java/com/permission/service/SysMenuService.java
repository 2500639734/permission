package com.permission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.dto.SysMenuTree;
import com.permission.dto.input.sysmenu.SysMenuInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.pojo.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 分页查询树形菜单列表
     * @param sysMenuInput 查询树形菜单列表入参
     * @return
     */
    IPage<SysMenuTree> selectSysMenuTreeList(SysMenuInput sysMenuInput);

    /**
     * 获取所有的菜单列表
     * @return
     */
    List<SysMenu> selectSysMenuList ();

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    SysMenu selectById (Integer id);

    /**
     * 菜单ids查询菜单列表
     * @param ids 菜单id集合
     * @return
     */
    List<SysMenu> selectRoleListByIds (Collection<Integer> ids);

    /**
     * 获取用户包含的菜单列表
     * @param userId 用户id
     * @return
     */
    List<SysMenu> selectMenuListByUserId (Integer userId);

    /**
     * 获取用户包含的菜单列表,树形结构
     * @param userId 用户id
     * @return
     */
    List<SysMenuTree> selectMenuTreeByUserId(Integer userId);

    /**
     * 获取角色包含的菜单列表
     * @param roleId
     * @return
     */
    List<SysMenu> selectMenuListByRoleId(Integer roleId);

    /**
     * 获取角色包含的菜单列表,树形结构
     * @param roleId 角色id
     * @return
     */
    List<SysMenuTree> selectMenuTreeByRoleId(Integer roleId);

    /**
     * 添加菜单
     * @param sysUserInfo 当前登录用户信息
     * @param sysMenuInput 添加菜单入参
     * @return
     */
    boolean addSysMenu (SysUserInfo sysUserInfo, SysMenuInput sysMenuInput);

}
