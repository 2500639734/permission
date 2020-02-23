package com.permission.dto.input.sysrole;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @auther: shenke
 * @date: 2020/2/23 11:08
 * @description: 查询角色列表入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SelectRoleInput {

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

}
