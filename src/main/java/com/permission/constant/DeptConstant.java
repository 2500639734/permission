package com.permission.constant;

/**
 * @auther: shenke
 * @date: 2019/10/30 21:18
 * @description: 部门相关常量
 */
public class DeptConstant {

    private DeptConstant () {

    }

    /*---------------------- 校验出错的提示常量Start ----------------------*/
    /** 部门id为空时校验失败的提示*/
    public static final String DEPT_ID_IS_NULL = "部门id不能为空";

    /** 部门上级部门id为空时校验失败的提示*/
    public static final String DEPT_PARENT_ID_IS_NULL = "部门上级部门id不能为空";

    /** 部门名称为空时校验失败的提示*/
    public static final String DEPT_NAME_IS_NULL = "部门名称不能为空";

    /** 部门名称长度错误时校验失败的提示*/
    public static final String DEPT_NAME_LENGTH_ERROR = "部门名称长度只能在2-15之间";

    /** 部门展示顺序为空时校验失败的提示*/
    public static final String DEPT_SQL_IS_NULL = "部门展示顺序不能为空";
    /*---------------------- 校验出错的提示常量End ----------------------*/

}
