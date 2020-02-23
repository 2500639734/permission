package com.permission.mapper;

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
     * 用户id查询角色列表
     * @param userId
     * @return
     */
    List<SysRole> selectRoleListByUserId (@Param("userId") Integer userId);

}
