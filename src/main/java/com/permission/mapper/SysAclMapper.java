package com.permission.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.annotation.Permission;
import com.permission.dto.input.sysacl.SysAclInput;
import com.permission.pojo.SysAcl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysAclMapper extends BaseMapper<SysAcl> {

    /**
     * 分页查询权限列表
     * @param page
     * @param sysAclInput
     * @return
     */
    IPage<SysAcl> selectAclList (@Param("page") Page page, @Param("sysAclInput") SysAclInput sysAclInput);

    /**
     * 获取用户包含的权限列表
     * @param userId
     * @param typeList 类型集合,可选
     * @return
     */
    List<SysAcl> selectPermissionListByUserId (@Param("userId") Integer userId, @Param("typeList") List<Integer> typeList);

}
