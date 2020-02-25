package com.permission.dto.output;

import com.permission.enumeration.SysAclTypeEnum;
import com.permission.pojo.SysAcl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * @auther: shenke
 * @date: 2020/2/25 19:58
 * @description: 权限查询返参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAclOutput {

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
     * 请求的url相对路径,如果是菜单则没有
     */
    private String url;

    /**
     * 权限类型：10-菜单权限，20-操作权限，30-其它
     */
    private Integer type;

    /**
     * 权限类型描述：菜单权限，操作权限，其它
     */
    private String typeDesc;

    /**
     * sysAcl -> SysAclOutput
     * @param sysAcl
     * @return
     */
    public static SysAclOutput toSysAclOutPut (SysAcl sysAcl) {
        SysAclOutput sysAclOutput = new SysAclOutput();
        BeanUtils.copyProperties(sysAcl, sysAclOutput);
        sysAclOutput.setTypeDesc(SysAclTypeEnum.getDescByCode(sysAcl.getType()));
        return sysAclOutput;
    }

}
