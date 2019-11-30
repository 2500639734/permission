package com.permission.service;

import com.permission.dto.SysUserDto;
import com.permission.dto.input.SysUserQueryInput;
import com.permission.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取部门树对应的用户列表
     * @param deptIdList
     * @return
     */
    List<SysUserDto> selectUserList (SysUserQueryInput sysUserQueryInput);

    /**
     * 删除系统用户
     * @param sysUserIdList
     * @return
     */
    boolean deleteSysUserList (List<Long> sysUserIdList);

}
