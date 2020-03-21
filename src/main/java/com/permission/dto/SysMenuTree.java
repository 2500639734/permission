package com.permission.dto;

import cn.hutool.core.collection.CollUtil;
import com.permission.constant.SysConstant;
import com.permission.enumeration.WhetherEnum;
import com.permission.pojo.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @auther: shenke
 * @date: 2020/3/6 14:30
 * @description: 菜单树形结构
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenuTree extends SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型描述
     */
    private String typeDesc;

    /**
     * 是否选中: 10-选中，20-未选中
     */
    private Integer checked;

    /**
     * 子菜单列表
     */
    private List<SysMenuTree> childMenuTreeList = new ArrayList<>();

    /**
     * SysMenu -> SysMenuTree
     * @param sysMenu
     * @return
     */
    public static SysMenuTree toSysMenuTree (SysMenu sysMenu) {
        SysMenuTree sysMenuTree = new SysMenuTree();
        BeanUtils.copyProperties(sysMenu, sysMenuTree);
        return sysMenuTree;
    }

    /**
     * SysMenuDto -> SysMenuTree
     * @param sysMenuDto
     * @return
     */
    public static SysMenuTree dtoToSysMenuTree (SysMenuDto sysMenuDto) {
        SysMenuTree sysMenuTree = new SysMenuTree();
        BeanUtils.copyProperties(sysMenuDto, sysMenuTree);
        return sysMenuTree;
    }

    /**
     * List<SysMenu> -> List<SysMenuTree>
     * @param sysMenuList
     * @return
     */
    public static List<SysMenuTree> toSysMenuTree (List<SysMenu> sysMenuList) {
        return sysMenuList.stream().map(sysMenu -> SysMenuTree.toSysMenuTree(sysMenu)).collect(Collectors.toList());
    }

    /**
     * List<SysMenuDto> -> List<SysMenuTree>
     * @param sysMenuDtoList
     * @return
     */
    public static List<SysMenuTree> dtoToSysMenuTree (List<SysMenuDto> sysMenuDtoList) {
        return sysMenuDtoList.stream().map(sysMenuDto -> SysMenuTree.toSysMenuTree(sysMenuDto)).collect(Collectors.toList());
    }

    /**
     * 获取菜单树的所有根节点
     * 仅限于调用buildSysMenuTree之前
     * @param sysMenuTreeList
     * @return
     */
    public static List<SysMenuTree> getRootMenuTreeList (List<SysMenuTree> sysMenuTreeList) {
        return sysMenuTreeList.stream().filter(sysMenuTree -> SysConstant.ROOT_ID.equals(sysMenuTree.getPid())).collect(Collectors.toList());
    }

    /**
     * 递归构建菜单树
     * @param sysMenuTreeList 所有的菜单集合
     * @param parentSysMenuTreeList 父级菜单集合
     * @return
     */
    public static List<SysMenuTree> buildSysMenuTree(List<SysMenuTree> sysMenuTreeList, List<SysMenuTree> parentSysMenuTreeList) {
        parentSysMenuTreeList.forEach(parentSysMenuTree -> {
            List<SysMenuTree> childSysMenuTreeList = new ArrayList<>();
            sysMenuTreeList.forEach(sysMenuTree -> {
                if (sysMenuTree.getPid().equals(parentSysMenuTree.getId())) {
                    childSysMenuTreeList.add(sysMenuTree);
                }
            });

            parentSysMenuTree.setChildMenuTreeList(buildSysMenuTree(sysMenuTreeList, childSysMenuTreeList));
        });

        return parentSysMenuTreeList;
    }

    /**
     * Map构建菜单树
     * @param sysMenuTreeList 所有的菜单集合,至少包含一个根菜单
     * @return
     */
    public static List<SysMenuTree> buildSysMenuTree(List<SysMenuTree> sysMenuTreeList) {
        // 所有菜单Map
        Map<Integer, SysMenuTree> sysMenuTreeMap = sysMenuTreeList.stream().collect(Collectors.toMap(SysMenuTree::getId, Function.identity(), (k1, k2) -> k2));

        // 子菜单的父级菜单childMenuTreeList集合中添加该子菜单
        sysMenuTreeList.stream()
                .filter(sysMenuTree -> ! SysConstant.ROOT_ID.equals(sysMenuTree.getPid()))
                .forEach(childSysMenuTree -> {
                    SysMenuTree sysMenuTree = sysMenuTreeMap.get(childSysMenuTree.getPid());
                    if (sysMenuTree != null) {
                        sysMenuTree.getChildMenuTreeList().add(childSysMenuTree);
                    }
                });

        // 过滤根节点返回
        return sysMenuTreeList.stream().filter(sysMenuTree -> SysConstant.ROOT_ID.equals(sysMenuTree.getPid())).collect(Collectors.toList());
    }

    /**
     * 获取指定菜单下的所有子菜单id
     * @param menuId 指定菜单id
     * @param sysMenuList 所有菜单 || 指定菜单的一级子菜单集合
     * @return
     */
    public static List<Integer> getChildMenuIdList(Integer menuId, List<SysMenu> sysMenuList) {
        List<Integer> childIdList = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            if (menuId.equals(sysMenu.getPid())) {
                childIdList.add(sysMenu.getId());
                if (WhetherEnum.YES.getCode().equals(sysMenu.getHasChild())) {
                    List<Integer> thatChildIdList = getChildMenuIdList(sysMenu.getId(),
                            sysMenuList.stream().filter(menu -> sysMenu.getId().equals(menu.getPid())).collect(Collectors.toList()));
                    if (CollUtil.isNotEmpty(thatChildIdList)) {
                        childIdList.addAll(thatChildIdList);
                    }
                }
            }
        });
        return childIdList;
    }

}
