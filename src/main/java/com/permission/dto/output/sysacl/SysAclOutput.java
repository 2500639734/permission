package com.permission.dto.output.sysacl;

import com.permission.enumeration.SysAclTypeEnum;
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
public class SysAclOutput extends SysAcl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限类型描述
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
