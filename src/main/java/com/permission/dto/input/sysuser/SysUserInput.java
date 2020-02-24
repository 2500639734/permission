package com.permission.dto.input.sysuser;

import com.permission.dto.input.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @auther: shenke
 * @date: 2020/2/22 8:40
 * @description: 用户相关接口入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserInput extends PageParam {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户编码
     */
    private String code;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 关联的角色id
     */
    private List<Integer> roleIdList;

}
