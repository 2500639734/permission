package com.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.SysUserDto;
import com.permission.dto.input.SysUserQueryInput;
import com.permission.enumeration.ResultEnum;
import com.permission.enumeration.SysUserStatusEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysUser;
import com.permission.mapper.SysUserMapper;
import com.permission.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUserDto> selectUserList(SysUserQueryInput sysUserQueryInput) {
        List<SysUserDto> sysUserDtoList = sysUserMapper.selectUserList(sysUserQueryInput);
        if (CollectionUtil.isNotEmpty(sysUserDtoList)) {
            // 状态转换
            sysUserDtoList.forEach(sysUserDto -> {
                sysUserDto.setStatusDesc(SysUserStatusEnum.getDescByCode(sysUserDto.getStatus()));
            });
        }

        return sysUserDtoList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUserList(List<Long> sysUserIdList) {
        List<SysUser> sysUserList = sysUserMapper.selectBatchIds(sysUserIdList);
        if (CollectionUtil.isEmpty(sysUserList)) {
            throw new BusinessException(ResultEnum.USER_NOT_EXISTS, sysUserIdList);
        }

        List<SysUser> deleteSysUserList = CollectionUtil.newArrayList();
        sysUserList.forEach(sysUser -> {
            sysUser.setStatus(SysUserStatusEnum.DELETED.getCode());
            deleteSysUserList.add(sysUser);
        });

        return updateBatchById(deleteSysUserList);
    }

}
