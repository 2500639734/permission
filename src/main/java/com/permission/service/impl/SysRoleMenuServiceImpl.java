package com.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.pojo.SysRoleMenu;
import com.permission.mapper.SysRoleMenuMapper;
import com.permission.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 查询角色已存在的角色菜单关联关系
     * @param roleId
     * @return
     */
    @Override
    public List<SysRoleMenu> selectSysRoleMenuByRoleId(Integer roleId) {
        if (roleId == null) {
            return null;
        }

        return sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
    }

    /**
     * 删除角色对应的角色菜单关联关系
     * @param roleId 角色id
     * @return
     */
    @Override
    public int deleteMenuByRoleId(Integer roleId) {
        if (roleId == null) {
            return 0;
        }

        return sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
    }

}
