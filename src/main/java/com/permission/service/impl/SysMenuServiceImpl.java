package com.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.dto.SysMenuDto;
import com.permission.dto.SysMenuTree;
import com.permission.dto.input.sysmenu.SysMenuInput;
import com.permission.enumeration.CheckedEnum;
import com.permission.enumeration.MenuTypeEnum;
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
     * 分页查询菜单列表
     * @param sysMenuInput 查询树形菜单列表入参
     * @return
     */
    @Override
    public IPage<SysMenuTree> selectSysMenuTreeList(SysMenuInput sysMenuInput) {
        // 分页查询根菜单集合
        Page<SysMenu> page = new Page<>(sysMenuInput.getPageStart(), sysMenuInput.getPageSize());
        IPage<SysMenu> sysMenuIPage = sysMenuMapper.selectSysMenuList(page, sysMenuInput.setRoot(true));
        List<SysMenuTree> rootSysMenuTreeList = SysMenuTree.toSysMenuTree(sysMenuIPage.getRecords())
                .stream()
                .map(sysMenuTree -> sysMenuTree.setTypeDesc(MenuTypeEnum.getDescByCode(sysMenuTree.getType())))
                .collect(Collectors.toList());

        // 查询所有菜单集合
        List<SysMenuTree> sysMenuTreeList = SysMenuTree.toSysMenuTree(selectSysMenuList());
        sysMenuTreeList
                .stream()
                .map(sysMenuTree -> sysMenuTree.setTypeDesc(MenuTypeEnum.getDescByCode(sysMenuTree.getType())))
                .collect(Collectors.toList());

        // 菜单集合转树形类型集合
        IPage<SysMenuTree> sysMenuTreePage = new Page<>();
        sysMenuTreePage.setTotal(sysMenuIPage.getTotal());
        sysMenuTreePage.setRecords(SysMenuTree.buildSysMenuTree(sysMenuTreeList, rootSysMenuTreeList));

        return sysMenuTreePage;
    }

    /**
     * 获取所有的菜单列表
     * @return
     */
    @Override
    public List<SysMenu> selectSysMenuList() {
        return sysMenuMapper.selectList(new QueryWrapper<SysMenu>().orderByAsc("sort"));
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

        // 构建菜单树返回
        return SysMenuTree.buildSysMenuTree(sysMenuTreeList);
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
        List<SysMenuDto> sysMenuDtoList = SysMenuDto.toSysMenuDto(selectSysMenuList()).stream().map(sysMenuDto -> {
            // 角色已拥有的菜单默认选中
            if (roleMenuIdList.contains(sysMenuDto.getId())) {
                sysMenuDto.setChecked(CheckedEnum.CHECKED.getCode());
            } else {
                sysMenuDto.setChecked(CheckedEnum.NO_CHECKED.getCode());
            }
            return sysMenuDto;
        }).collect(Collectors.toList());

        // 构建菜单树返回
        return SysMenuTree.buildSysMenuTree(SysMenuTree.dtoToSysMenuTree(sysMenuDtoList));
    }

}
