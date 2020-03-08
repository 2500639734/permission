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

    GET (10),
    POST (20),
    PUT (30),
    DELETE (40),
    ALL (50);

    /**
     * 唯一标识
     */
    private Integer code;

    /**
     * 根据唯一标识获取权限类型名称
     * @param code
     * @return
     */
    public static String getNameByCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (SysAclTypeEnum sysAclTypeEnum : SysAclTypeEnum.values()) {
            if (sysAclTypeEnum.code == code) {
                return sysAclTypeEnum.name();
            }
        }

        return null;
    }

}
