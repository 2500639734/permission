package com.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.permission.pojo.SysRoleMenu;
import com.permission.mapper.SysRoleMenuMapper;
import com.permission.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 删除角色对应的角色菜单关联关系
     * @param roleId 角色id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleMenuByRoleId(Integer roleId) {
        if (roleId == null) {
            return 0;
        }

        return sysRoleMenuMapper.delete(new UpdateWrapper<SysRoleMenu>().eq("role_id", roleId));
    }

}
