package com.permission.dto.input.sysrole;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @auther: shenke
 * @date: 2020/2/23 9:50
 * @description: 添加角色入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AddSysRoleInput {

    /**
     * 角色名称
     */
    private String name;

}
