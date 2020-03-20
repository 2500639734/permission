package com.permission.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.dto.SysRoleDto;
import com.permission.dto.input.sysrole.SysRoleInput;
import com.permission.pojo.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询角色列表
     * @param page
     * @param sysRoleInput
     * @return
     */
    IPage<SysRoleDto> selectSysRoleList (@Param("page") Page page, @Param("sysRoleInput") SysRoleInput sysRoleInput);

    /**
     * 用户id查询角色列表
     * @param userId
     * @return
     */
    List<SysRole> selectSysRoleListByUserId (@Param("userId") Integer userId);

}
