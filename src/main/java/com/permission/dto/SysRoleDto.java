package com.permission.dto;

import com.permission.enumeration.CheckedEnum;
import com.permission.pojo.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @auther: shenke
 * @date: 2020/3/8 16:30
 * @description: 角色查询返参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRoleDto extends SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否选中：
     *  10-是，20-否
     * 若用户已拥有此角色则选中
     */
    private Integer checked = CheckedEnum.NO_CHECKED.getCode();

}
