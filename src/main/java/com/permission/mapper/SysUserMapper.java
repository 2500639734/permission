package com.permission.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.dto.input.sysuser.SysUserInput;
import com.permission.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 分页查询用户列表
     * @param page 分页对象,必须放在第一位,自动分页
     * @param sysUserInput 用户查询入参
     * @return
     */
    IPage<SysUser> selectSysUserList (@Param("page") Page page, @Param("sysUserInput") SysUserInput sysUserInput);

}
