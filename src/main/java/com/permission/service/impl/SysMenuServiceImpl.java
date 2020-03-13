package com.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.SysMenuDto;
import com.permission.dto.SysMenuTree;
import com.permission.enumeration.CheckedEnum;
import com.permission.pojo.SysMenu;
import com.permission.mapper.SysMenuMapper;
import com.permission.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 获取所有的菜单列表
     * @return
     */
    @Override
    public List<SysMenu> selectMenuList() {
        return sysMenuMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 菜单ids查询菜单列表
     * @param ids 菜单id集合
     * @return
     */
    @Override
    public List<SysMenu> selectRoleListByIds(Collection<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }

        return sysMenuMapper.selectBatchIds(ids);
    }

    /**
     * 获取用户包含的菜单列表
     * @param userId 用户id
     * @return
     */
    @Override
    public List<SysMenu> selectMenuListByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }

        return sysMenuMapper.selectMenuListByUserId(userId);
    }

    /**
     * 获取用户包含的菜单列表,树形结构
     * @param userId 用户id
     * @return
     */
    @Override
    public List<SysMenuTree> selectMenuTreeByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }

        // 获取用户拥有的菜单集合
        List<SysMenu> sysMenuList = selectMenuListByUserId(userId);
        // 菜单集合转树形类型集合
        List<SysMenuTree> sysMenuTreeList = SysMenuTree.toSysMenuTree(sysMenuList);
        // 获取所有的一级菜单集合
        List<SysMenuTree> rootMenuTreeList = SysMenuTree.getRootMenuTreeList(sysMenuTreeList);

        // 构建菜单树返回
        return SysMenuTree.buildSysMenuTree(sysMenuTreeList, rootMenuTreeList);
    }

    /**
     * 获取角色包含的菜单列表
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> selectMenuListByRoleId(Integer roleId) {
        if (roleId == null) {
            return null;
        }

        return sysMenuMapper.selectMenuListByRoleId(roleId);
    }

    /**
     * 获取角色包含的菜单列表,树形结构
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenuTree> selectMenuTreeByRoleId(Integer roleId) {
        if (roleId == null) {
            return null;
        }

        // 获取角色拥有的菜单id集合
        List<Integer> roleMenuIdList = selectMenuListByRoleId(roleId).stream().map(SysMenu::getId).collect(Collectors.toList());
        // 获取所有的菜单列表
        List<SysMenuDto> sysMenuDtoList = SysMenuDto.toSysMenuDto(selectMenuList()).stream().map(sysMenuDto -> {
            // 角色已拥有的菜单默认选中
            if (roleMenuIdList.contains(sysMenuDto.getId())) {
                sysMenuDto.setChecked(CheckedEnum.CHECKED.getCode());
            }
            return sysMenuDto;
        }).collect(Collectors.toList());

        // 构建菜单树返回
        return SysMenuTree.buildSysMenuTree(SysMenuTree.dtoToSysMenuTree(sysMenuDtoList));
    }

}
