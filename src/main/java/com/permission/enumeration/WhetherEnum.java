package com.permission.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther: shenke
 * @date: 2020/3/8 16:48
 * @description: 是否通用枚举,适用仅有两种选择的情况
 */
@AllArgsConstructor
@Getter
public enum WhetherEnum {

    /**
     * 是
     */
    YES (10, true, "是"),

    /**
     * 否
     */
    NO (20, false, "否");

    /**
     * 唯一编码，通用
     */
    private Integer code;

    /**
     * 布尔值，通用
     */
    private Boolean bValue;

    /**
     * 描述
     */
    private String desc;

}
