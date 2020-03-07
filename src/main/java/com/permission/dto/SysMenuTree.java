package com.permission.dto;

import com.permission.pojo.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

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
     * 子菜单列表
     */
    private List<SysMenuTree> childMenuTreeList;

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

}
