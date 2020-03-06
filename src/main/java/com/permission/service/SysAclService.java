package com.permission.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.permission.dto.input.sysacl.SysAclInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.output.sysacl.SysAclOutput;
import com.permission.dto.output.sysacl.SysAclTree;
import com.permission.pojo.SysAcl;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
public interface SysAclService extends IService<SysAcl> {

    /**
     * 分页查询权限列表
     * @param sysAclInput
     * @return
     */
    IPage<SysAclOutput> selectAclList (SysAclInput sysAclInput);

    /**
     * 添加权限
     * @param sysUserInfo 当前登录用户信息
     * @param sysAclInput 添加权限入参
     * @return
     */
    SysAcl addSysAcl(SysUserInfo sysUserInfo, SysAclInput sysAclInput);

    /**
     * 更新权限
     * @param sysUserInfo 当前登录用户信息
     * @param sysAclInput 更新权限入参
     * @return
     */
    SysAcl updateSysAcl(SysUserInfo sysUserInfo, SysAclInput sysAclInput);

    /**
     * 删除权限
     * @param id 权限id
     * @return
     */
    int deleteSysAcl (Integer id);

    /**
     * 获取用户拥有的权限列表
     * @param userId
     * @param typeList
     * @return
     */
    List<SysAcl> selectAclListByUserId (Integer userId, List<Integer> typeList);

    /**
     * 判断用户是否有访问接口权限
     * @param userId
     * @param request
     * @return
     */
    boolean hasAcl (Integer userId, HttpServletRequest request);

    /**
     * 根据父级权限id查询父级权限
     * @param pid 父级权限id
     * @return
     */
    SysAcl selectParentAclByPid (Integer pid);

    /**
     * 根据权限名称查询权限
     * @param name 权限名称
     * @return
     */
    SysAcl selectAclByName (String name);

    /**
     * 根据权限编码查询权限
     * @param code 权限编码
     * @return
     */
    SysAcl selectAclByCode (String code);

    /**
     * 根据ids查询权限集合
     * @param ids 权限id集合
     * @return
     */
    List<SysAcl> selectAclsByIds (List<Integer> ids);

}
