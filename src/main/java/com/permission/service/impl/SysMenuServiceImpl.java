package com.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.permission.constant.SysConstant;
import com.permission.dto.SysMenuTree;
import com.permission.pojo.SysMenu;
import com.permission.mapper.SysMenuMapper;
import com.permission.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<SysMenuTree> sysMenuTreeList = new ArrayList<>();

        // 菜单集合转树形菜单集合\获取所有的一级菜单集合
        List<SysMenuTree> rootMenuTreeList = new ArrayList<>();
        if (CollUtil.isNotEmpty(sysMenuList)) {
            sysMenuTreeList = sysMenuList.stream().map(sysMenu -> SysMenuTree.toSysMenuTree(sysMenu)).collect(Collectors.toList());
            rootMenuTreeList = sysMenuTreeList.stream().filter(sysMenuTree -> SysConstant.ROOT_ID.equals(sysMenuTree.getPId())).collect(Collectors.toList());
        }

        // 构建菜单树返回
        return buildSysMenuTree(sysMenuTreeList, rootMenuTreeList);
    }

    /**
     * 构建菜单树
     * @param sysMenuTreeList 所有的菜单集合
     * @param parentSysMenuTreeList 父级菜单集合
     * @return
     */
    private List<SysMenuTree> buildSysMenuTree (List<SysMenuTree> sysMenuTreeList, List<SysMenuTree> parentSysMenuTreeList) {
        parentSysMenuTreeList.forEach(parentSysMenuTree -> {
            List<SysMenuTree> childSysMenuTreeList = new ArrayList<>();
            sysMenuTreeList.forEach(sysMenuTree -> {
                if (sysMenuTree.getPId() == parentSysMenuTree.getId()) {
                    childSysMenuTreeList.add(sysMenuTree);
                }
            });

            parentSysMenuTree.setChildMenuTreeList(buildSysMenuTree(sysMenuTreeList, childSysMenuTreeList));
        });

        return parentSysMenuTreeList;
    }

}
