package com.permission.dto.input;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @auther: shenke
 * @date: 2020/2/23 6:22
 * @description: 用户登录返参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserLoginOutput {

    /**
     * 用户信息
     */
    private SysUserInfo sysUserInfo;

    /**
     * 用户登录成功的Token
     */
    private String token;

}
