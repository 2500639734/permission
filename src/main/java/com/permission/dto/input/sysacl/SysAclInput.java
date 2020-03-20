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
     * 权限名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 接口url
     */
    private String url;

    /**
     * 请求方式：10-GET，20-POST，30-PUT，40-DELETE，50-ALL
     */
    private Integer type;

    /**
     * 搜索条件：
     * 权限名 | 权限编码
     */
    private String search;

    /**
     * 创建日期开始查询条件
     */
    private String createDateStart;

    /**
     * 创建日期结束查询条件
     */
    private String createDateEnd;

    /**
     * 角色id
     */
    private Integer roleId;

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
