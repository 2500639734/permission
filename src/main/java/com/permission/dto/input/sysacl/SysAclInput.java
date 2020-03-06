package com.permission.dto.input.sysacl;

import com.permission.dto.input.PageParam;
import com.permission.pojo.SysAcl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * @auther: shenke
 * @date: 2020/2/25 18:46
 * @description: 权限相关操作入参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAclInput extends PageParam {

    /**
     * 权限id
     */
    private Integer id;

    /**
     * 父级权限id：0-没有父级权限
     */
    private Integer pId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 菜单：前端的路由路径，按钮及其它：接口路径
     */
    private String url;

    /**
     * 菜单及按钮的图标，可选
     */
    private String icon;

    /**
     * 权限类型：10-菜单权限，20-操作(按钮)权限，30-其它
     */
    private Integer type;

    /**
     * SysAclInput -> SysAcl
     * @param sysAclInput
     * @return
     */
    public static SysAcl toSysAcl (SysAclInput sysAclInput) {
        SysAcl sysAcl = new SysAcl();
        BeanUtils.copyProperties(sysAclInput, sysAcl);
        return sysAcl;
    }

}
