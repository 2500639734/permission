package com.permission.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther: shenke
 * @date: 2020/3/14 15:36
 * @description: 菜单类型枚举
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    MENU (10, "菜单"),
    BUTTON (20, "按钮");

    /**
     * 菜单类型编码
     */
    private Integer code;

    /**
     * 菜单类型描述
     */
    private String desc;

    public static String getDescByCode (Integer code) {
        if (code == null) {
            return null;
        }

        for (MenuTypeEnum menuTypeEnum : MenuTypeEnum.values()) {
            if (menuTypeEnum.code.equals(code)) {
                return menuTypeEnum.desc;
            }
        }

        return null;
    }

}
