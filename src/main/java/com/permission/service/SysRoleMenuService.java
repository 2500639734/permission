package com.permission.service;
import com.permission.pojo.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 删除角色对应的角色菜单关联关系
     * @param roleId 角色id
     * @return
     */
    int deleteRoleMenuByRoleId (Integer roleId);

}
