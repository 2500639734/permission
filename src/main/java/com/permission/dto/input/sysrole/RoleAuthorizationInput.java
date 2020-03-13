package com.permission.dto.input.sysrole;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @auther: shenke
 * @date: 2020/3/11 21:19
 * @description: 角色授权入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoleAuthorizationInput implements Serializable {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 授权的菜单id集合
     */
    private List<Integer> authorizationMenuIdList;

}
