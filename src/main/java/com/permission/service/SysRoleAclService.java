package com.permission.service;

import com.permission.pojo.SysRoleAcl;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 删除角色关联的权限
     * @param roleId 角色id
     * @return
     */
    int deleteRoleAcls (Integer roleId);

}
