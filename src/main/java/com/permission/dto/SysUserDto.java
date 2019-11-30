package com.permission.dto;

import com.permission.pojo.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @auther: shenke
 * @date: 2019/11/5 21:29
 * @description: 系统用户Dto
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserDto extends SysUser implements Serializable {

    /**
     * 系统用户状态描述(0冻结，1正常，2删除)
     */
    private String statusDesc;

    /**
     * 所属部门名称
     */
    private String deptName;

}
