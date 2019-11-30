package com.permission.mapper;

import com.permission.dto.SysUserDto;
import com.permission.dto.input.SysUserQueryInput;
import com.permission.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUserDto> selectUserList (@Param("sysUserQueryInput") SysUserQueryInput sysUserQueryInput);

}
