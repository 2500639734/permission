package com.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.input.sysrole.AddSysRoleInput;
import com.permission.dto.input.sysrole.SelectRoleInput;
import com.permission.dto.input.sysuser.CasUserInfo;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.enumeration.RegexEnum;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysRole;
import com.permission.mapper.SysRoleMapper;
import com.permission.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.PinyinUtils;
import com.permission.util.ValidatedUtils;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2020-02-21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询角色列表
     * @param selectRoleInput 查询角色列表入参
     * @return
     */
    @Override
    public List<SysRole> selectRoleList(SelectRoleInput selectRoleInput) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();

        // 选择性按角色编码或角色名称模糊查询
        if (selectRoleInput != null) {
            if (StringUtils.isNotEmpty(selectRoleInput.getCode())) {
                queryWrapper.likeRight("code", selectRoleInput.getCode()).or();
            }
            if (StringUtils.isNotEmpty(selectRoleInput.getName())) {
                queryWrapper.likeRight("name", selectRoleInput.getName()).or();
            }
        }

        return sysRoleMapper.selectList(queryWrapper);
    }

    /**
     * 角色ids查询角色列表
     * @param ids
     * @return
     */
    @Override
    public List<SysRole> selectRoleListByIds(Collection<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }

        return sysRoleMapper.selectBatchIds(ids);
    }

    /**
     * 用户id查询角色列表
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> selectRoleListByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }

        return sysRoleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 根据角色编码查询角色
     * @param code
     * @return
     */
    @Override
    public SysRole selectRoleByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }

        return sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("code", code));
    }

    /**
     * 添加角色
     * @param sysUserInfo 当前登录用户信息
     * @param sysRoleInput 添加角色入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(SysUserInfo sysUserInfo, AddSysRoleInput sysRoleInput) {
        // 参数校验
        ValidatedUtils.objectIsNuLL(sysRoleInput, ResultEnum.PARAM_ERROR);
        ValidatedUtils.objectIsNuLL(sysRoleInput.getName(), ResultEnum.ROLE_NAME_IS_NULL);
        ValidatedUtils.strIsMatchRegex(sysRoleInput.getName(), RegexEnum.ROLE_NAME.getRegex(), ResultEnum.ROLE_NAME_NOT_REGEX);

        // 校验角色是否存在
        String code = PinyinUtils.getPingYin(sysRoleInput.getName(), HanyuPinyinCaseType.LOWERCASE);
        SysRole selectRole = selectRoleByCode(code);
        if (selectRole != null) {
            throw new BusinessException(ResultEnum.ROLE_EXISTS);
        }

        // 构建角色信息
        SysRole sysRole = new SysRole().setName(sysRoleInput.getName())
                .setCode(code)
                .setCreateTime(new Date())
                .setCreateUserId(sysUserInfo.getId())
                .setCreateUserName(sysUserInfo.getName())
                .setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getName());

        // 保存角色
        int insertNumber = sysRoleMapper.insert(sysRole);
        if (insertNumber <= 0) {
            throw new BusinessException(ResultEnum.ADD_ROLE_FAIL);
        }

        return sysRole;
    }

}
