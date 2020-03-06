package com.permission.dto.output.sysacl;

import com.permission.enumeration.SysAclTypeEnum;
import com.permission.pojo.SysAcl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @auther: shenke
 * @date: 2020/3/5 4:11
 * @description: 权限树
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAclTree extends SysAclOutput implements Serializable {

    /**
     * 子权限集合
     */
    private List<SysAclTree> childSysAclList;

    /**
     * sysAcl -> SysAclTree
     * @param sysAcl
     * @return
     */
    public static SysAclTree toSysAclTree (SysAcl sysAcl) {
        SysAclTree sysAclTree = new SysAclTree();
        BeanUtils.copyProperties(sysAcl, sysAclTree);
        sysAclTree.setTypeDesc(SysAclTypeEnum.getDescByCode(sysAcl.getType()));
        return sysAclTree;
    }

}
