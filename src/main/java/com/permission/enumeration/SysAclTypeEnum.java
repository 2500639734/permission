package com.permission.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther: shenke
 * @date: 2020/2/25 19:52
 * @description: 权限类型枚举
 */
@AllArgsConstructor
@Getter
public enum SysAclTypeEnum {

    MENU (10, "菜单权限"),
    BUTTON (20, "按钮权限"),
    INTERFACE(30, "接口访问权限");

    /**
     * 唯一标识
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 根据编码获取描述
     * @param code
     * @return
     */
    public static String getDescByCode (Integer code) {
        if (code == null) {
            return null;
        }

        for (SysAclTypeEnum sysAclTypeEnum : SysAclTypeEnum.values()) {
            if (sysAclTypeEnum.getCode().equals(code)) {
                return sysAclTypeEnum.getDesc();
            }
        }

        return null;
    }

}
