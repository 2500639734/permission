package com.permission.dto.input.sysrole;

import com.permission.dto.input.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @auther: shenke
 * @date: 2020/2/23 11:08
 * @description: 角色相关操作入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRoleInput extends PageParam {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色编码,自动生成
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色授权id集合
     */
    private List<Integer> aclIds;

    /**
     * 搜索条件：
     * 角色名 | 角色编码
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
     * 用户id，授权角色时默认选中时传入
     */
    private Integer userId;

}
