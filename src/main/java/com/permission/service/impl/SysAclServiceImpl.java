package com.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.permission.enumeration.ResultEnum;
import com.permission.pojo.SysAcl;
import com.permission.mapper.SysAclMapper;
import com.permission.service.SysAclService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.ValidatedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@Service
public class SysAclServiceImpl extends ServiceImpl<SysAclMapper, SysAcl> implements SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    public List<SysAcl> selectPermissionListByUserId(Integer userId) {
        // 参数校验
        ValidatedUtils.objectIsNuLL(userId, ResultEnum.PARAM_ERROR);

        return sysAclMapper.selectPermissionListByUserId(userId);
    }

    @Override
    public boolean isPermission(Integer userId, HttpServletRequest request) {
        List<SysAcl> permissionList = selectPermissionListByUserId(userId);
        if (CollectionUtil.isNotEmpty(permissionList)) {
            // 获取用户有权限访问的接口集合
            Set<String> permissionSet = permissionList.stream().map(SysAcl::getUrl).collect(Collectors.toSet());
            // 是否包含当前请求的接口
            return permissionSet.contains(request.getRequestURI());
        }

        return false;
    }

}
