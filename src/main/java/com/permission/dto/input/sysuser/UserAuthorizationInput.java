package com.permission.dto.input.sysuser;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @auther: shenke
 * @date: 2020/3/8 17:57
 * @description: 用户授权角色/取消授权入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAuthorizationInput implements Serializable {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 授权的角色id集合
     */
    private List<Integer> authorizationRoleIdList;

}
