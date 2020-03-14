package com.permission.dto;

import com.permission.enumeration.CheckedEnum;
import com.permission.pojo.SysAcl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
     * 权限类型对应的请求方式名称
     */
    private String methodTypeName;

    /**
     * 是否选中：10-选中，20-未选中，默认未选中
     */
    private Integer checked = CheckedEnum.NO_CHECKED.getCode();

}
