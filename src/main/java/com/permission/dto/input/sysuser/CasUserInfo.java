package com.permission.dto.input.sysuser;

import com.permission.dto.SysMenuTree;
import com.permission.pojo.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @auther: shenke
 * @date: 2020/2/23 6:22
 * @description: 用户登录存储对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CasUserInfo {

    /**
     * 用户信息
     */
    private SysUserInfo sysUserInfo;

    /**
     * 用户所包含的角色列表
     */
    private List<SysRole> sysRoleList;

    /**
     * 用户所包含的菜单树形列表
     */
    private List<SysMenuTree> sysMenuTreeList;

}
