package com.permission.service;

import com.permission.dto.input.SysUserInfo;
import com.permission.dto.input.SysUserLoginInput;
import com.permission.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     * @param userLoginInput
     * @return
     */
    SysUserInfo login (SysUserLoginInput userLoginInput);

    /**
     * 用户退出登录
     * @param request
     */
    void logout (HttpServletRequest request);

}
