package com.permission.dto.input;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @auther: shenke
 * @date: 2019/11/6 22:14
 * @description: 查询用户列表入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserQueryInput implements Serializable {

    /**
     * 查询内容: 按用户名/手机号/邮箱/部门搜索
     */
    private String search;

    /**
     * 部门id列表
     */
    private List<Long> deptIdList;

}
