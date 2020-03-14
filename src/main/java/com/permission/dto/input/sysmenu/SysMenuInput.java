package com.permission.dto.input.sysmenu;

import com.permission.dto.input.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 是否只查询根节点：true-是，false-否
     */
    private boolean root;

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

}
