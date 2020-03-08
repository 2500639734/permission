package com.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.input.sysuser.UserAuthorizationInput;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysRole;
import com.permission.pojo.SysUser;
import com.permission.pojo.SysUserRole;
import com.permission.mapper.SysUserRoleMapper;
import com.permission.service.SysRoleService;
import com.permission.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.service.SysUserService;
import com.permission.util.ValidatedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询用户已存在的用户角色关联关系
     * @param userId
     * @return
     */
    @Override
    public List<SysUserRole> selectSysUserRoleByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }

        return sysUserRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", userId));
    }

    /**
     * 添加用户角色关系
     * @param sysUserInfo 当前登录用户信息
     * @param userAuthorizationInput 用户授权角色入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addUserRoles(SysUserInfo sysUserInfo, UserAuthorizationInput userAuthorizationInput) {
        // 校验参数
        ValidatedUtils.objectIsNuLL(userAuthorizationInput, ResultEnum.PARAM_ERROR);
        Integer userId = userAuthorizationInput.getUserId();
        List<Integer> roleIdList = userAuthorizationInput.getAuthorizationRoleIdList();
        ValidatedUtils.objectIsNuLL(userId, ResultEnum.PARAM_ERROR);
        ValidatedUtils.collectionIsNull(roleIdList, ResultEnum.NO_SELECTD_ROLE);

        // 校验用户是否存在
        SysUser sysUser = sysUserService.selectUserById(userId);
        ValidatedUtils.objectIsNuLL(sysUser, ResultEnum.USER_NOT_EXISTS);

        // 校验角色是否存在
        List<SysRole> existsRoleList = sysRoleService.selectRoleListByIds(roleIdList);
        ValidatedUtils.collectionIsNull(existsRoleList, ResultEnum.ROLE_NOT_EXISTS);
        List<Integer> existsRoleIdList = existsRoleList.stream().map(SysRole::getId).collect(Collectors.toList());
        if (! existsRoleIdList.containsAll(roleIdList)) {
            throw new BusinessException(ResultEnum.ROLE_NOT_EXISTS);
        }

        // 过滤已存在的用户角色关系
        List<SysUserRole> existsSysUserRoleList = selectSysUserRoleByUserId(userId);
        if (CollectionUtil.isNotEmpty(existsSysUserRoleList)) {
            List<Integer> existsUserRoleIdList = existsSysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            roleIdList.removeAll(existsUserRoleIdList);
        }

        // 若关联的角色全部存在关联关系则不重复添加
        if (CollectionUtil.isEmpty(roleIdList)) {
            return true;
        }

        // 保存用户角色关系
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        roleIdList.forEach(roleId -> {
            sysUserRoleList.add(
                    new SysUserRole()
                            .setUserId(userId)
                            .setRoleId(roleId)
            );
        });

        // 更新用户操作人信息
        sysUser.setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getUsername());
        sysUserService.updateById(sysUser);

        return saveBatch(sysUserRoleList);
    }

    /**
     * 删除用户关联的角色
     * @param userId 用户id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteUserRoles(Integer userId) {
        // 校验参数
        ValidatedUtils.objectIsNuLL(userId, ResultEnum.PARAM_ERROR);

        // 删除用户角色关系
        return sysUserRoleMapper.delete(
                new QueryWrapper<SysUserRole>().eq("user_id", userId)
        );
    }

    /**
     * 删除角色关联的用户
     * @param roleId 角色id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteRoleUsers(Integer roleId) {
        // 校验参数
        ValidatedUtils.objectIsNuLL(roleId, ResultEnum.PARAM_ERROR);

        // 删除角色用户关系
        return sysUserRoleMapper.delete(
                new QueryWrapper<SysUserRole>().eq("role_id", roleId)
        );
    }
}
