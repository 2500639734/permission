package com.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.enumeration.ResultEnum;
import com.permission.pojo.SysRoleAcl;
import com.permission.mapper.SysRoleAclMapper;
import com.permission.service.SysRoleAclService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.ValidatedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@Service
public class SysRoleAclServiceImpl extends ServiceImpl<SysRoleAclMapper, SysRoleAcl> implements SysRoleAclService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * 删除角色关联的权限
     * @param roleId 角色id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteRoleAcls(Integer roleId) {
        // 参数校验
        ValidatedUtils.objectIsNuLL(roleId, ResultEnum.PARAM_ERROR);

        // 删除角色关联的权限
        return sysRoleAclMapper.delete(new QueryWrapper<SysRoleAcl>().eq("role_id", roleId));
    }

}
