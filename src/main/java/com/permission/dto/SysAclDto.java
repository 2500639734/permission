package com.permission.dto;

import com.permission.enumeration.RequestTypeEnum;
import com.permission.pojo.SysAcl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @auther: shenke
 * @date: 2020/2/25 19:58
 * @description: 权限查询返参
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAclDto extends SysAcl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限类型对应的请求方式
     */
    private String typeMethod;

    /**
     * sysAcl -> SysAclOutput
     * @param sysAcl
     * @return
     */
    public static SysAclDto toSysAclOutPut (SysAcl sysAcl) {
        SysAclDto sysAclOutput = new SysAclDto();
        BeanUtils.copyProperties(sysAcl, sysAclOutput);
        sysAclOutput.setTypeMethod(RequestTypeEnum.getNameByCode(sysAcl.getType()));
        return sysAclOutput;
    }

}
