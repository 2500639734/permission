package com.permission.service;

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
     * 获取用户包含的权限列表
     * @param userId
     * @return
     */
    List<SysAcl> selectPermissionListByUserId (Integer userId);

    /**
     * 判断用户是否有访问接口权限
     * @param userId
     * @param request
     * @return
     */
    boolean isPermission (Integer userId, HttpServletRequest request);

}
