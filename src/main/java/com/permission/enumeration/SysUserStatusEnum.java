package com.permission.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther: shenke
 * @date: 2019/11/5 21:49
 * @description: 系统用户状态枚举
 */
@AllArgsConstructor
@Getter
public enum SysUserStatusEnum {

    /**
     * 用户状态枚举
     */
    FREEZE(0, "冻结"),
    NORMAL (1, "正常"),
    DELETED(2, "删除");

    private Integer code;

    private String desc;

    /**
     * 根据编码获取描述
     * @param code
     * @return
     */
    public static String getDescByCode(Integer code) {
        for (SysUserStatusEnum sysUserStatusEnum : SysUserStatusEnum.values()) {
            if (sysUserStatusEnum.code.equals(code)) {
                return sysUserStatusEnum.desc;
            }
        }
        return null;
    }

}
