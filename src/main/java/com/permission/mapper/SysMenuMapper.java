package com.permission.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.dto.input.sysmenu.SysMenuInput;
import com.permission.pojo.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 分页查询菜单列表
     * @param page 分页对象,必须放在第一位,自动分页
     * @param sysMenuInput 菜单查询入参
     * @return
     */
    IPage<SysMenu> selectSysMenuList(@Param("page") Page<SysMenu> page, @Param("sysMenuInput") SysMenuInput sysMenuInput);

    /**
     * 获取用户包含的菜单列表
     * @param userId 用户id
     * @return
     */
    List<SysMenu> selectMenuListByUserId (@Param("userId") Integer userId);

    /**
     * 获取角色包含的菜单列表
     * @param roleId 角色id
     * @return
     */
    List<SysMenu> selectMenuListByRoleId (@Param("roleId") Integer roleId);

}
