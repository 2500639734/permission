package com.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.dto.SysMenuTree;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.input.sysuser.SysUserLoginInput;
import com.permission.dto.input.sysuser.CasUserInfo;
import com.permission.dto.input.sysuser.SysUserInput;
import com.permission.enumeration.PrimaryCodeEnum;
import com.permission.enumeration.RegexEnum;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysUser;
import com.permission.mapper.SysUserMapper;
import com.permission.service.SysMenuService;
import com.permission.service.SysUserRoleService;
import com.permission.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public IPage<SysUser> selectSysUserList(SysUserInput sysUserInput) {
        Page<SysUser> page = new Page<>(sysUserInput.getPageStart(), sysUserInput.getPageSize());
        return sysUserMapper.selectSysUserList(page, sysUserInput);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public SysUser selectUserById(Integer id) {
        if (id == null) {
            return null;
        }

        return sysUserMapper.selectById(id);
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return
     */
    @Override
    public SysUser selectUserByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    /**
     * 添加用户
     * @param sysUserInfo 当前登录的用户信息
     * @param sysUserInput 添加用户入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUserInfo addUser(SysUserInfo sysUserInfo, SysUserInput sysUserInput) {
        // 参数校验
        validUserInput(sysUserInput, false);

        // 根据用户名查询用户
        SysUser selectUser = selectUserByUsername(sysUserInput.getUsername());

        // 用户已存在
        ValidatedUtils.objectIsNotNuLL(selectUser, ResultEnum.USER_EXISTS);

        // 保存用户
        String passwordSalt = EncryptionUtils.uuid();
        SysUser sysUser = new SysUser().setCode(PrimaryCodeUtils.createPrimaryCode(PrimaryCodeEnum.USER))
                .setName(sysUserInput.getName())
                .setUsername(sysUserInput.getUsername())
                .setPassword(EncryptionUtils.getPassword(sysUserInput.getPassword(), passwordSalt))
                .setPasswordSalt(passwordSalt)
                .setCreateTime(new Date())
                .setCreateUserId(sysUserInfo.getId())
                .setCreateUserName(sysUserInfo.getName())
                .setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getName());

        int saveCount = sysUserMapper.insert(sysUser);
        if (saveCount <= 0) {
            throw new BusinessException(ResultEnum.ADD_USER_FAIL);
        }

        // 维护用户角色关系
        sysUserRoleService.addUserRoles(sysUser.getId(), sysUserInput.getRoleIdList());

        return SysUserInfo.toSysUserInfo(sysUser);
    }

    /**
     * 更新用户
     * @param sysUserInfo 当前登录的用户信息
     * @param sysUserInput 修改用户入参
     * @return
     */
    @Override
    public SysUserInfo updateUser(SysUserInfo sysUserInfo, SysUserInput sysUserInput) {
        // 参数校验
        validUserInput(sysUserInput, true);

        // 用户是否存在
        SysUser sysUser = selectUserById(sysUserInput.getId());
        ValidatedUtils.objectIsNuLL(sysUser, ResultEnum.USER_NOT_EXISTS);

        // 修改了用户名校验修改的用户名是否存在
        if (! sysUserInput.getUsername().equals(sysUser.getUsername())) {
            SysUser selectSysUser = selectUserByUsername(sysUserInput.getUsername());
            ValidatedUtils.objectIsNotNuLL(selectSysUser, ResultEnum.USERNAME_EXISTS);
        }

        // 重新加密密码
        String newPassword = EncryptionUtils.getPassword(sysUserInput.getPassword(), sysUser.getPasswordSalt());

        // 更新用户
        sysUser.setName(sysUserInput.getName())
                .setUsername(sysUserInput.getUsername())
                .setPassword(newPassword)
                .setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getName());

        int updateNumber = sysUserMapper.updateById(sysUser);
        if (updateNumber <= 0) {
            throw new BusinessException(ResultEnum.UPDATE_USER_FAIL);
        }

        // 维护用户角色关系
        sysUserRoleService.addUserRoles(sysUser.getId(), sysUserInput.getRoleIdList());

        return SysUserInfo.toSysUserInfo(sysUser);
    }

    /**
     * 删除用户
     * @param userId 用户id
     * @return
     */
    @Override
    public Integer deleteUser(Integer userId) {
        // 校验参数
        ValidatedUtils.objectIsNuLL(userId, ResultEnum.PARAM_ERROR);

        // 用户是否存在
        SysUser sysUser = sysUserMapper.selectById(userId);
        ValidatedUtils.objectIsNuLL(sysUser, ResultEnum.USER_NOT_EXISTS);

        // 删除用户
        int deleteNumber = sysUserMapper.deleteById(userId);
        if (deleteNumber <= 0) {
            throw new BusinessException(ResultEnum.DELETE_USER_FAIL);
        }

        // 删除用户关联的角色
        sysUserRoleService.deleteUserRoles(userId);

        return userId;
    }

    /**
     * 用户相关操作入参校验
     * @param isUpdate 是否为更新校验
     * @param sysUserInput
     */
    private void validUserInput (SysUserInput sysUserInput, boolean isUpdate) {
        // 必填参数校验
        ValidatedUtils.objectIsNuLL(sysUserInput, ResultEnum.PARAM_ERROR);
        if (isUpdate) {
            ValidatedUtils.objectIsNuLL(sysUserInput.getId(), ResultEnum.PARAM_ERROR);
        }
        ValidatedUtils.strIsNull(sysUserInput.getName(), ResultEnum.NAME_IS_NULL);
        ValidatedUtils.strIsMatchRegex(sysUserInput.getName(), RegexEnum.NAME.getRegex(), ResultEnum.NAME__NOT_REGEX);
        ValidatedUtils.strIsNull(sysUserInput.getUsername(), ResultEnum.USERNAME_IS_NULL);
        ValidatedUtils.strIsMatchRegex(sysUserInput.getUsername(), RegexEnum.USERNAME.getRegex(), ResultEnum.USERNAME_NOT_REGEX);
        ValidatedUtils.strIsNull(sysUserInput.getPassword(), ResultEnum.PASSWORD_IS_NULL);
        ValidatedUtils.strIsMatchRegex(sysUserInput.getUsername(), RegexEnum.PASSWORD.getRegex(), ResultEnum.PASSWORD_NOT_REGEX);
        ValidatedUtils.collectionIsNull(sysUserInput.getRoleIdList(), ResultEnum.NO_SELECTD_ROLE);
    }

    /**
     * 用户登录
     * @param sysUserLoginInput 系统用户登录入参
     * @return
     */
    @Override
    public String login(HttpServletResponse response, SysUserLoginInput sysUserLoginInput) {
        // 参数校验
        ValidatedUtils.objectIsNuLL(sysUserLoginInput, ResultEnum.PARAM_ERROR);
        ValidatedUtils.strIsNull(sysUserLoginInput.getUsername(), ResultEnum.USERNAME_IS_NULL);
        ValidatedUtils.objectIsNuLL(sysUserLoginInput.getPassword(), ResultEnum.PASSWORD_IS_NULL);

        // 根据用户名查询用户
        SysUser sysUser = selectUserByUsername(sysUserLoginInput.getUsername());

        // 用户不存在
        if (sysUser == null) {
            throw new BusinessException(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        // 校验密码
        String password = EncryptionUtils.getPassword(sysUserLoginInput.getPassword(), sysUser.getPasswordSalt());
        if (! sysUser.getPassword().equals(password)) {
            throw new BusinessException(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        // 设置用户拥有的菜单树形列表
        List<SysMenuTree> sysMenuTreeList = sysMenuService.selectMenuTreeByUserId(sysUser.getId());
        CasUserInfo casUserInfo = new CasUserInfo()
                .setSysUserInfo(SysUserInfo.toSysUserInfo(sysUser))
                .setSysMenuTreeList(sysMenuTreeList);

        // 保存token到cookie
        String token = sysUser.getCode();
        RedisUtils.set(token, casUserInfo, EncryptionUtils.LOGIIN_TOKEN_DEFAULT_TIME_OUT_MS);

        // 保存token到cookie
        CookieUtils.setLoginToke(response, token);

        return token;
    }

    /**
     * 用户退出登录
     * @param request
     */
    @Override
    public void logout(HttpServletRequest request) {
        // 从cookie中获取token信息
        String tokenKey = CookieUtils.getLoginToken(request);
        // 删除Redis中的Token信息
        if (StringUtils.isNotEmpty(tokenKey)) {
            RedisUtils.del(tokenKey);
        }
    }

}
