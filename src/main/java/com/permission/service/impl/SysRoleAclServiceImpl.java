package com.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.input.sysrole.RoleAuthorizationInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysAcl;
import com.permission.pojo.SysRole;
import com.permission.pojo.SysRoleAcl;
import com.permission.mapper.SysRoleAclMapper;
import com.permission.service.SysAclService;
import com.permission.service.SysRoleAclService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.service.SysRoleService;
import com.permission.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysAclService sysAclService;

    /**
     * 查询角色已存在的角色权限关联关系
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<SysRoleAcl> selectSysRoleAclByRoleId(Integer roleId) {
        if (roleId == null) {
            return null;
        }

        return sysRoleAclMapper.selectList(new QueryWrapper<SysRoleAcl>().eq("role_id", roleId));
    }

    /**
     * 角色授权权限
     * @param sysUserInfo 当前登录的用户信息
     * @param roleAuthorizationInput 角色授权权限入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addRoleAcls(SysUserInfo sysUserInfo, RoleAuthorizationInput roleAuthorizationInput) {
        // 校验参数
        SysRole sysRole = validAuthorizationParam(roleAuthorizationInput);

        // 过滤原本已存在的用户角色关系
        List<Integer> aclIdList = roleAuthorizationInput.getAuthorizationAclIdList();
        List<Integer> oldAclIds = selectSysRoleAclByRoleId(roleAuthorizationInput.getRoleId()).stream().map(SysRoleAcl::getAclId).collect(Collectors.toList());
        aclIdList = aclIdList.stream().filter(aclId -> ! oldAclIds.contains(aclId)).collect(Collectors.toList());

        // 授权的角色原本已有权限则不重复添加
        if (CollectionUtil.isEmpty(aclIdList)) {
            return true;
        }

        // 更新用户操作人信息
        sysRole.setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getName());
        sysRoleService.updateById(sysRole);

        // 保存用户角色关系
        List<SysRoleAcl> sysRoleAclList = new ArrayList<>();
        aclIdList.forEach(aclId -> {
            sysRoleAclList.add(
                    new SysRoleAcl()
                            .setRoleId(roleAuthorizationInput.getRoleId())
                            .setAclId(aclId)
            );
        });

        return saveBatch(sysRoleAclList);
    }

    /**
     * 取消角色授权的权限
     * @param sysUserInfo 当前登录的用户信息
     * @param roleAuthorizationInput 取消用户授权角色入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRoleAcls(SysUserInfo sysUserInfo, RoleAuthorizationInput roleAuthorizationInput) {
        // 校验参数
        SysRole sysRole = validAuthorizationParam(roleAuthorizationInput);

        // 更新角色操作人信息
        sysRole.setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getName());
        sysRoleService.updateById(sysRole);

        // 删除用户角色关系
        return sysRoleAclMapper.delete(
                new QueryWrapper<SysRoleAcl>()
                        .eq("role_id", roleAuthorizationInput.getRoleId()).in("acl_id", roleAuthorizationInput.getAuthorizationAclIdList())
        ) > 0;
    }

    /**
     * 授权角色权限/取消授权 校验参数
     * @param roleAuthorizationInput 授权 / 取消授权入参
     * @return 用户信息
     */
    private SysRole validAuthorizationParam (RoleAuthorizationInput roleAuthorizationInput) {
        // 校验参数
        ObjectUtils.isNull(roleAuthorizationInput, ResultEnum.PARAM_ERROR);
        Integer roleId = roleAuthorizationInput.getRoleId();
        List<Integer> aclIdList = roleAuthorizationInput.getAuthorizationAclIdList();
        ObjectUtils.isNull(roleId, ResultEnum.PARAM_ERROR);
        ObjectUtils.collectionIsNull(aclIdList, ResultEnum.NO_SELECTD_ACL);

        // 校验角色是否存在
        SysRole sysRole = sysRoleService.selectRoleById(roleId);
        ObjectUtils.isNull(sysRole, ResultEnum.ROLE_NOT_EXISTS);

        // 校验权限是否存在
        List<SysAcl> aclList = sysAclService.selectAclsByIds(aclIdList);
        if (CollUtil.isEmpty(aclList)) {
            throw new BusinessException(ResultEnum.ACL_NOT_EXISTS);
        }

        return sysRole;
    }

}
