package com.permission.dto.input.sysrole;

import com.permission.dto.input.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

}
