package com.permission.mapper;

import com.permission.pojo.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 删除角色对应的菜单
     * @param roleId 角色id
     * @return
     */
    int deleteMenuByRoleId(Integer roleId);

}
