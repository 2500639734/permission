package com.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.permission.constant.SysConstant;
import com.permission.dto.SysMenuDto;
import com.permission.dto.SysMenuTree;
import com.permission.dto.input.sysmenu.SysMenuInput;
import com.permission.dto.input.sysuser.SysUserInfo;
import com.permission.enumeration.MenuTypeEnum;
import com.permission.enumeration.RegexEnum;
import com.permission.enumeration.ResultEnum;
import com.permission.enumeration.WhetherEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysMenu;
import com.permission.mapper.SysMenuMapper;
import com.permission.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        // 分页查询菜单集合
        Page<SysMenuTree> page = new Page<>(sysMenuInput.getPageStart(), sysMenuInput.getPageSize());
        IPage<SysMenuTree> sysMenuTreeIPage = sysMenuMapper.selectSysMenuList(page, sysMenuInput);

        // 设置菜单类型描述
        sysMenuTreeIPage.getRecords().stream()
                .forEach(sysMenuTree -> sysMenuTree.setTypeDesc(MenuTypeEnum.getDescByCode(sysMenuTree.getType())));

        // 过滤所有的父菜单
        List<SysMenuTree> parentSysMenuTree = sysMenuTreeIPage.getRecords().stream()
                .filter(sysMenuTree -> SysConstant.ROOT_ID.equals(sysMenuTree.getPid()) || WhetherEnum.YES.getBValue().equals(sysMenuTree.getHasChild()))
                .collect(Collectors.toList());

        // 若查询结果没有父菜单则直接返回
        if (CollectionUtil.isEmpty(parentSysMenuTree)) {
            return sysMenuTreeIPage;
        }

        // 构建菜单树返回（若搜索结果中不包含子菜单则不展示子菜单）
        return sysMenuTreeIPage.setRecords(SysMenuTree.buildSysMenuTree(sysMenuTreeIPage.getRecords(), parentSysMenuTree));
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
     * 根据id查询菜单
     * @param id
     * @return
     */
    @Override
    public SysMenu selectById(Integer id) {
        if (id == null) {
            return null;
        }
        return sysMenuMapper.selectById(id);
    }

    /**
     * 根据pid查询菜单
     * @param pid 父菜单id
     * @return
     */
    @Override
    public List<SysMenu> selectByPid(Integer pid) {
        if (pid == null) {
            return null;
        }
        return sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("pid", pid));
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
                sysMenuDto.setChecked(WhetherEnum.YES.getCode());
            } else {
                sysMenuDto.setChecked(WhetherEnum.NO.getCode());
            }
            return sysMenuDto;
        }).collect(Collectors.toList());

        // 构建菜单树返回
        return SysMenuTree.buildSysMenuTree(SysMenuTree.dtoToSysMenuTree(sysMenuDtoList));
    }

    /**
     * 添加菜单
     * @param sysUserInfo 当前登录用户信息
     * @param sysMenuInput 添加菜单入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSysMenu(SysUserInfo sysUserInfo, SysMenuInput sysMenuInput) {
        // 参数校验
        validMenu (sysMenuInput);

        // 菜单顺序是否已存在
        Set<Integer> sortSet = selectSysMenuList().stream().map(SysMenu::getSort).collect(Collectors.toSet());
        ObjectUtils.isContains(sysMenuInput.getSort(), sortSet, ResultEnum.MENU_SORT_EXISTS);

        SysMenu sysMenu = SysMenuInput.toSysMenu(sysMenuInput)
                .setCreateTime(new Date())
                .setCreateUserId(sysUserInfo.getId())
                .setCreateUserName(sysUserInfo.getName())
                .setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getName());

        // 添加子菜单
        if (sysMenuInput.getPid() == null || sysMenuInput.getPid().equals(SysConstant.ROOT_ID)) {
            sysMenu.setPid(SysConstant.ROOT_ID);
        } else {
            // 添加父菜单,更新父菜单hasChild
            SysMenu parentSysMenu = selectById(sysMenuInput.getPid());
            if (WhetherEnum.NO.getCode().equals(parentSysMenu.getHasChild())) {
                parentSysMenu.setHasChild(WhetherEnum.YES.getCode());
                sysMenuMapper.updateById(parentSysMenu);
            }
        }

        // 添加菜单
        if (sysMenuMapper.insert(sysMenu) <= 0) {
            throw new BusinessException(ResultEnum.MENU_ADD_ERROR);
        }

    }

    /**
     * 修改菜单
     * @param sysUserInfo 当前登录用户信息
     * @param sysMenuInput 添加菜单入参
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSysMenu(SysUserInfo sysUserInfo, SysMenuInput sysMenuInput) {
        // 参数校验
        validMenu (sysMenuInput);

        // 菜单是否存在
        SysMenu oldSysMenu = selectById(sysMenuInput.getId());
        ObjectUtils.isNull(oldSysMenu, ResultEnum.MENU_NOT_EXISTS);

        // 修改了菜单顺序
        if (! oldSysMenu.getSort().equals(sysMenuInput.getSort())) {
            // 菜单顺序是否已存在
            Set<Integer> sortSet = selectSysMenuList().stream().map(SysMenu::getSort).collect(Collectors.toSet());
            ObjectUtils.isContains(sysMenuInput.getSort(), sortSet, ResultEnum.MENU_SORT_EXISTS);
        }

        SysMenu sysMenu = SysMenuInput.toSysMenu(sysMenuInput)
                .setUpdateTime(new Date())
                .setUpdateUserId(sysUserInfo.getId())
                .setUpdateUserName(sysUserInfo.getName());

        // 修改菜单
        if (sysMenuMapper.updateById(sysMenu) <= 0) {
            throw new BusinessException(ResultEnum.MENU_UPDATE_ERROR);
        }
    }

    /**
     * 删除菜单
     * @param id 菜单id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSysMenu(Integer id) {
        // 参数校验
        ObjectUtils.isNull(id, ResultEnum.MENU_NOT_EXISTS);
        SysMenu sysMenu = selectById(id);
        ObjectUtils.isNull(sysMenu, ResultEnum.MENU_NOT_EXISTS);

        // 获取指定菜单及菜单下的所有子菜单id集合
        List<Integer> childMenuIdList = SysMenuTree.getChildMenuIdList(id, selectSysMenuList());
        childMenuIdList.add(id);

        // 删除指定菜单及菜单下的所有子菜单
        int deleteNums = sysMenuMapper.deleteBatchIds(childMenuIdList);
        if (deleteNums <= 0) {
            throw new BusinessException(ResultEnum.MENU_DELETE_ERROR);
        }

        // 若指定菜单的父级菜单不为根菜单,且未包含其它子菜单则将has_child置为否
        if (! SysConstant.ROOT_ID.equals(sysMenu.getPid())) {
            long childCount = selectByPid(sysMenu.getPid()).stream().filter(childSysMenu -> ! id.equals(childSysMenu.getId())).count();
            if (childCount <= 0) {
                sysMenuMapper.updateById(selectById(sysMenu.getPid()).setHasChild(WhetherEnum.NO.getCode()));
            }
        }

    }

    /**
     * 校验菜单操作项
     * @param sysMenuInput
     */
    private void validMenu (SysMenuInput sysMenuInput) {
        ObjectUtils.isNull(sysMenuInput, ResultEnum.PARAM_ERROR);
        ObjectUtils.strIsMatchRegex(sysMenuInput.getName(), RegexEnum.NAME.getRegex(), ResultEnum.MENU_NAME_NOT_REGEX);
        ObjectUtils.strIsMatchRegex(sysMenuInput.getPath(), RegexEnum.MENU_PATH.getRegex(), ResultEnum.MENU_PATH_NOT_REGEX);
        ObjectUtils.strIsMatchRegex(sysMenuInput.getIcon(), RegexEnum.MENU_ICON.getRegex(), ResultEnum.MENU_ICON_NOT_REGEX);
        ObjectUtils.isContains(ResultEnum.MENU_TYPE_ERROR, sysMenuInput.getType(), WhetherEnum.YES.getCode(), WhetherEnum.NO.getCode());
    }

}
