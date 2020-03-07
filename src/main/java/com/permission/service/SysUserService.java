package com.permission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.dto.input.sysuser.CasUserInfo;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.input.sysuser.SysUserLoginInput;
import com.permission.dto.input.sysuser.SysUserInput;
import com.permission.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 分页查询用户列表
     * @param sysUserInput
     * @return
     */
    IPage<SysUser> selectSysUserList (SysUserInput sysUserInput);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    SysUser selectUserById (Integer id);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return
     */
    SysUser selectUserByUsername (String username);

    /**
     * 添加用户
     * @param sysUserInfo 当前登录的用户信息
     * @param sysUserInput 添加用户入参
     * @return
     */
    SysUserInfo addUser (SysUserInfo sysUserInfo,SysUserInput sysUserInput);

    /**
     * 更新用户
     * @param sysUserInfo 当前登录的用户信息
     * @param sysUserInput 修改用户入参
     * @return
     */
    SysUserInfo updateUser (SysUserInfo sysUserInfo, SysUserInput sysUserInput);

    /**
     * 删除用户
     * @param userId 用户id
     * @return
     */
    Integer deleteUser (Integer userId);

    /**
     * 用户登录
     * @param response
     * @param userLoginInput 用户登录入参
     * @return
     */
    String login (HttpServletResponse response, SysUserLoginInput userLoginInput);

    /**
     * 用户退出登录
     * @param request
     */
    void logout (HttpServletRequest request);

}
