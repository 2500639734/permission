package com.permission.dto.input.sysuser;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
     * 用户登录成功的Token
     */
    private String token;

}
