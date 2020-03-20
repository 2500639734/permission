package com.permission.dto.input.sysmenu;

import com.permission.dto.input.PageParam;
import com.permission.pojo.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * @auther: shenke
 * @date: 2020/3/9 11:54
 * @description: 菜单查询入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenuInput extends PageParam {

    /**
     * 菜单id
     */
    private Integer id;

    /**
     * 父菜单id
     */
    private Integer pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单类型：10-菜单，20-按钮
     */
    private Integer type;

    /**
     * 顺序,数字从小到大升序
     */
    private Integer sort;

    /**
     * 是否包含子菜单：10-是，20-否
     */
    private Integer hasChild;

    /**
     * 前端路由路径
     */
    private String path;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 搜索条件：
     * 菜单名称
     */
    private String search;

    /**
     * 创建日期开始查询条件
     */
    private String createDateStart;

    /**
     * 创建日期结束查询条件
     */
    private String createDateEnd;

    /**
     * SysMenuInput -> SysMenu
     * @param sysMenuInput
     * @return
     */
    public static SysMenu toSysMenu (SysMenuInput sysMenuInput) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuInput, sysMenu);
        return sysMenu;
    }

}
