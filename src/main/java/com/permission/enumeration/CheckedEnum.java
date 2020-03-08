package com.permission.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther: shenke
 * @date: 2020/3/8 16:48
 * @description: 是否选中枚举
 */
@AllArgsConstructor
@Getter
public enum CheckedEnum {

    /**
     * 选中
     */
    CHECKED (10),
    /**
     * 未选中
     */
    NO_CHECKED (20);

    private Integer code;

}
