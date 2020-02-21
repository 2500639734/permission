package com.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.input.SysUserInfo;
import com.permission.dto.input.SysUserLoginInput;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysUser;
import com.permission.mapper.SysUserMapper;
import com.permission.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.EncryptionUtils;
import com.permission.util.RedisUtils;
import com.permission.util.ValidatedUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
     * 系统用户登录
     * @param sysUserLoginInput 系统用户登录入参
     * @return
     */
    @Override
    public SysUserInfo login(SysUserLoginInput sysUserLoginInput) {

        // 参数校验
        ValidatedUtils.objectIsNuLL(sysUserLoginInput, ResultEnum.PARAM_ERROR);
        ValidatedUtils.strIsNullOr(ResultEnum.LOGIN_FAIL_USERNAME_OR_PHONE_IS_NULL, sysUserLoginInput.getUsername(), sysUserLoginInput.getPhone());
        ValidatedUtils.objectIsNuLL(sysUserLoginInput.getPassword(), ResultEnum.LOGIN_FAIL_PASSWORD_IS_NULL);

        // 根据用户名或手机号查询用户
        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .eq("username", sysUserLoginInput.getUsername())
                .or()
                .eq("phone", sysUserLoginInput.getPhone()));

        // 用户不存在
        if (sysUser == null) {
            throw new BusinessException(ResultEnum.LOGIN_FAIL_USER_NOT_EXISTS, sysUserLoginInput.getUsername(), sysUserLoginInput.getPhone());
        }

        // 校验密码
        String password = EncryptionUtils.getPassword(sysUserLoginInput.getPassword(), sysUser.getPasswordSalt());
        if (! sysUser.getPassword().equals(password)) {
            throw new BusinessException(ResultEnum.LOGIN_FAIL_BY_PASSWORD_ERROR, sysUserLoginInput.getUsername(), sysUserLoginInput.getPhone());
        }

        // 构建用户信息
        String token = EncryptionUtils.token(EncryptionUtils.LOGIIN_TOKEN_PREFIX);
        SysUserInfo sysUserInfo = new SysUserInfo();
        BeanUtils.copyProperties(sysUser, sysUserInfo);
        sysUserInfo.setToken(token);

        // 保存到Redis
        RedisUtils.set(token, sysUserInfo, EncryptionUtils.LOGIIN_TOKEN_DEFAULT_TIME_OUT_MS);

        return sysUserInfo;
    }

    @Override
    public void logout(HttpServletRequest request) {
        String tokenKey = request.getHeader(EncryptionUtils.LOGIIN_TOKEN_KEY);
        // 删除Redis中的Token信息
        if (StringUtils.isNotEmpty(tokenKey)) {
            RedisUtils.del(tokenKey);
        }
    }

}
