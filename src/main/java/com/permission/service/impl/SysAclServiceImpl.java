package com.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.constant.SysConstant;
import com.permission.dto.input.sysacl.SysAclInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.dto.SysAclDto;
import com.permission.enumeration.RegexEnum;
import com.permission.enumeration.ResultEnum;
import com.permission.enumeration.RequestTypeEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysAcl;
import com.permission.mapper.SysAclMapper;
import com.permission.service.SysAclService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
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

    /**
     * 分页查询权限列表
     * @param sysAclInput
     * @return
     */
    @Override
    public IPage<SysAclDto> selectAclList(SysAclInput sysAclInput) {
        // 分页获取权限列表
        Page page = new Page(sysAclInput.getPageStart(), sysAclInput.getPageSize());
        IPage<SysAcl> sysAclIPage = sysAclMapper.selectAclList(page, sysAclInput);
        List<SysAcl> sysAclList = sysAclIPage.getRecords();
        List<SysAclDto> sysAclOutputList = new ArrayList<>();

        // 类型转换
        if (CollectionUtil.isNotEmpty(sysAclList)) {
            sysAclList.forEach(sysAcl -> sysAclOutputList.add(SysAclDto.toSysAclOutPut(sysAcl)));
        }

        // 返回
        IPage<SysAclDto> sysAclOutputIPage = new Page<>();
        sysAclOutputIPage.setRecords(sysAclOutputList);
        sysAclOutputIPage.setTotal(sysAclIPage.getTotal());
        return sysAclOutputIPage;
    }

    /**
     * 添加权限
     * @param sysUserInfo 当前登录用户信息
     * @param sysAclInput 添加权限入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysAcl addSysAcl(SysUserInfo sysUserInfo, SysAclInput sysAclInput) {
        // 参数校验
        validSysAclInput(sysAclInput, false);

        // 添加权限
        SysAcl sysAcl = SysAclInput.toSysAcl(sysAclInput)
                .setCreateTime(new Date())
                .setCreateUserId(sysUserInfo.getCreateUserId())
                .setCreateUserName(sysUserInfo.getCreateUserName())
                .setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getUpdateUserName());
        int addSysAclNumber = sysAclMapper.insert(sysAcl);

        if (addSysAclNumber <= 0) {
            throw new BusinessException(ResultEnum.SUCCESS);
        }

        return sysAcl;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysAcl updateSysAcl(SysUserInfo sysUserInfo, SysAclInput sysAclInput) {
        // 参数校验
        validSysAclInput(sysAclInput, true);

        // 权限是否存在
        SysAcl updateSysAcl = sysAclMapper.selectById(sysAclInput.getId());
        ObjectUtils.isNull(updateSysAcl, ResultEnum.ACL_NOT_EXISTS);

        // 修改了名称则验证名称是否已存在
        if (! sysAclInput.getName().equals(updateSysAcl.getName())) {
            SysAcl sysAclByName = selectAclByName(sysAclInput.getName());
            ObjectUtils.isNotNull(sysAclByName, ResultEnum.ACL_NAME_EXISTS);
        }

        // 修改了编码则验证编码是否已存在
        if (! sysAclInput.getCode().equals(updateSysAcl.getCode())) {
            SysAcl sysAclByCode = selectAclByCode(sysAclInput.getCode());
            ObjectUtils.isNotNull(sysAclByCode, ResultEnum.ACL_CODE_EXISTS);
        }

        updateSysAcl.setId(sysAclInput.getId())
                .setPId(sysAclInput.getPId())
                .setName(sysAclInput.getName())
                .setCode(sysAclInput.getCode())
                .setUrl(sysAclInput.getUrl())
                .setType(sysAclInput.getType())
                .setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getUpdateUserId())
                .setUpdateUserName(sysUserInfo.getUpdateUserName());

        int updateAclNums = sysAclMapper.updateById(updateSysAcl);
        if (updateAclNums <= 0) {
            throw new BusinessException(ResultEnum.UPDATE_ACL_FILA);
        }

        return updateSysAcl;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSysAcl(Integer id) {
        // 参数校验
        ObjectUtils.isNull(id, ResultEnum.PARAM_ERROR);

        // 权限是否存在
        SysAcl sysAcl = sysAclMapper.selectById(id);
        ObjectUtils.isNull(sysAcl, ResultEnum.ACL_NOT_EXISTS);

        // 删除权限
        int deleteNums = sysAclMapper.deleteById(id);
        if (deleteNums <= 0) {
            throw new BusinessException(ResultEnum.DELETE_ACL_FILA);
        }

        return deleteNums;
    }

    /**
     * 校验权限相关操作入参
     * @param sysAclInput
     * @param isUpdate 是否是更新
     */
    private void validSysAclInput (SysAclInput sysAclInput, boolean isUpdate) {
        ObjectUtils.isNull(sysAclInput, ResultEnum.PARAM_ERROR);
        if (isUpdate) {
            ObjectUtils.isNull(sysAclInput.getId(), ResultEnum.PARAM_ERROR);
        }
        ObjectUtils.isNull(sysAclInput.getPId(), ResultEnum.PARAM_ERROR);
        // 有父级权限则校验父级权限是否存在
        if (! SysConstant.ROOT_ID.equals(sysAclInput.getPId())) {
            SysAcl parentSysAcl = selectParentAclByPid(sysAclInput.getPId());
            ObjectUtils.isNull(parentSysAcl, ResultEnum.PARENT_ACL_NOT_EXISTS);
        }
        ObjectUtils.isNull(sysAclInput.getName(), ResultEnum.ACL_NAME_IS_NULL);
        ObjectUtils.strIsMatchRegex(sysAclInput.getName(), RegexEnum.ACL_NAME.getRegex(), ResultEnum.ACL_NAME_NOT_REGEX);
        ObjectUtils.isNull(sysAclInput.getCode(), ResultEnum.ACL_CODE_IS_NULL);
        ObjectUtils.strIsMatchRegex(sysAclInput.getCode(), RegexEnum.ACL_CODE.getRegex(), ResultEnum.ACL_CODE_NOT_REGEX);
        ObjectUtils.isNull(sysAclInput.getType(), ResultEnum.ACL_TYPE_IS_NULL);
    }

    /**
     * 获取用户包含的权限列表
     * @param userId 用户id
     * @return
     */
    @Override
    public List<SysAcl> selectAclListByUserId(Integer userId) {
        // 参数校验
        ObjectUtils.isNull(userId, ResultEnum.PARAM_ERROR);

        return sysAclMapper.selectPermissionListByUserId(userId);
    }

    /**
     * 判断用户是否有访问接口权限
     * @param userId 用户id
     * @param aclCode 当前接口的权限标识
     * @param request
     * @return
     */
    @Override
    public boolean hasAcl(Integer userId, String aclCode, HttpServletRequest request) {
        // 获取用户有权限访问的接口集合
        List<SysAcl> sysAclList = selectAclListByUserId(userId);
        if (CollectionUtil.isNotEmpty(sysAclList)) {

            // 转换权限集合
            Map<String, Integer> syaAclUriMap = sysAclList.stream().collect(Collectors.toMap(SysAcl::getUrl, SysAcl::getType));
            Map<String, Integer> syaAclCodeMap = sysAclList.stream().collect(Collectors.toMap(SysAcl::getCode, SysAcl::getType));

            // 接口权限不足
            if (! syaAclUriMap.containsKey(request.getRequestURI()) && ! syaAclCodeMap.containsKey(aclCode)) {
                return false;
            }

            // 获取权限允许的请求方式
            Integer type = syaAclUriMap.get(request.getRequestURI());
            if (type == null) {
                type = syaAclCodeMap.get(aclCode);
            }

            // 请求方式不匹配
            if (! RequestTypeEnum.ALL.getCode().equals(type)
                && ! RequestTypeEnum.getNameByCode(type).equals(request.getMethod())) {
                return false;
            }

            // 权限验证通过
            return true;
        }

        return false;
    }

    /**
     * 根据父级权限id查询父级权限
     * @param pId 父级权限id
     * @return
     */
    @Override
    public SysAcl selectParentAclByPid(Integer pId) {
        if (pId == null || SysConstant.ROOT_ID.equals(pId)) {
            return null;
        }

        return sysAclMapper.selectOne(new QueryWrapper<SysAcl>().eq("id", pId));
    }

    /**
     * 根据权限名称查询权限
     * @param name 权限名称
     * @return
     */
    @Override
    public SysAcl selectAclByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        return sysAclMapper.selectOne(new QueryWrapper<SysAcl>().eq("name", name));
    }

    /**
     * 根据权限编码查询权限
     * @param code 权限编码
     * @return
     */
    @Override
    public SysAcl selectAclByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }

        return sysAclMapper.selectOne(new QueryWrapper<SysAcl>().eq("code", code));
    }

    /**
     * 根据ids查询权限集合
     * @param ids 权限id集合
     * @return
     */
    @Override
    public List<SysAcl> selectAclsByIds(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }

        return sysAclMapper.selectBatchIds(ids);
    }

}
