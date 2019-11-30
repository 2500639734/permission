package com.permission.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysAcl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限模块名称
     */
    private String name;

    /**
     * 权限所在的权限模块id
     */
    private Long aclModelId;

    /**
     * 请求的url
     */
    private String url;

    /**
     * 权限类型，1：菜单，2：按钮，3：其它
     */
    private Integer type;

    /**
     * 状态，1：正常，0：冻结
     */
    private Integer status;

    /**
     * 权限模块在当前层级下的顺序,升序
     */
    private Integer seq;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 最后一次更新时间
     */
    private Date operatorTime;

    /**
     * 最后一次更新者的ip地址
     */
    private String operatorIp;


}
