package com.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.input.SysUserInfo;
import com.permission.dto.input.SysUserLoginInput;
import com.permission.dto.input.SysUserLoginOutput;
import com.permission.dto.input.SysUserRegisterInput;
import com.permission.enumeration.PrimaryCodeEnum;
import com.permission.enumeration.RegexEnum;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysUser;
import com.permission.mapper.SysUserMapper;
import com.permission.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.EncryptionUtils;
import com.permission.util.PrimaryCodeUtils;
import com.permission.util.RedisUtils;
import com.permission.util.ValidatedUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    /**
     * 用户注册
     * @param sysUserRegisterInput
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUserInfo addUser(SysUserRegisterInput sysUserRegisterInput) {
        // 参数校验
        addUserInputValid(sysUserRegisterInput);

        // 保存用户
        SysUser sysUser = new SysUser();
        sysUser.setCode(PrimaryCodeUtils.createPrimaryCode(PrimaryCodeEnum.USER));
        sysUser.setName(sysUserRegisterInput.getName());
        sysUser.setUsername(sysUserRegisterInput.getUsername());
        String passwordSalt = EncryptionUtils.uuid();
        sysUser.setPassword(EncryptionUtils.getPassword(sysUserRegisterInput.getPassword(), passwordSalt));
        sysUser.setPasswordSalt(passwordSalt);
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(new Date());
        boolean save = save(sysUser);
        if (! save) {
            throw new BusinessException(ResultEnum.REGISTER_FAIL);
        }

        return SysUserInfo.toSysUserInfo(sysUser);
    }

    /**
     * 用户注册入参校验
     * @param sysUserRegisterInput
     */
    private void addUserInputValid (SysUserRegisterInput sysUserRegisterInput) {
        // 必填参数校验
        ValidatedUtils.objectIsNuLL(sysUserRegisterInput, ResultEnum.PARAM_ERROR);
        ValidatedUtils.strIsNull(sysUserRegisterInput.getName(), ResultEnum.NAME_IS_NULL);
        ValidatedUtils.strIsMatchRegex(sysUserRegisterInput.getName(), RegexEnum.NAME_MAX_LENGTH.getRegex(), ResultEnum.NAME__NOT_REGEX);
        ValidatedUtils.strIsMatchRegex(sysUserRegisterInput.getUsername(), RegexEnum.USERNAME_MAX_LENGTH.getRegex(), ResultEnum.USERNAME_NOT_REGEX);
        ValidatedUtils.strIsNull(sysUserRegisterInput.getPassword(), ResultEnum.PASSWORD_IS_NULL);
    }

    /**
     * 用户登录
     * @param sysUserLoginInput 系统用户登录入参
     * @return
     */
    @Override
    public SysUserLoginOutput login(SysUserLoginInput sysUserLoginInput) {
        // 参数校验
        ValidatedUtils.objectIsNuLL(sysUserLoginInput, ResultEnum.PARAM_ERROR);
        ValidatedUtils.strIsNull(sysUserLoginInput.getUsername(), ResultEnum.USERNAME_IS_NULL);
        ValidatedUtils.objectIsNuLL(sysUserLoginInput.getPassword(), ResultEnum.PASSWORD_IS_NULL);

        // 根据用户名查询用户
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .eq("username", sysUserLoginInput.getUsername()));

        // 用户不存在
        if (sysUser == null) {
            throw new BusinessException(ResultEnum.USER_NOT_EXISTS);
        }

        // 校验密码
        String password = EncryptionUtils.getPassword(sysUserLoginInput.getPassword(), sysUser.getPasswordSalt());
        if (! sysUser.getPassword().equals(password)) {
            throw new BusinessException(ResultEnum.PASSWORD_ERROR);
        }

        // 保存token
        String token = sysUser.getCode();
        SysUserInfo sysUserInfo = SysUserInfo.toSysUserInfo(sysUser);
        RedisUtils.set(token, sysUserInfo, EncryptionUtils.LOGIIN_TOKEN_DEFAULT_TIME_OUT_MS);

        return new SysUserLoginOutput()
                .setSysUserInfo(sysUserInfo)
                .setToken(token);
    }

    /**
     * 用户退出登录
     * @param request
     */
    @Override
    public void logout(HttpServletRequest request) {
        String tokenKey = request.getHeader(EncryptionUtils.LOGIIN_TOKEN_KEY);
        // 删除Redis中的Token信息
        if (StringUtils.isNotEmpty(tokenKey)) {
            RedisUtils.del(tokenKey);
        }
    }

}
