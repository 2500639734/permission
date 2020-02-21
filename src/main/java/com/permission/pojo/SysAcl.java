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
 * @since 2020-02-21
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
    private Integer id;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 请求的url相对路径
     */
    private String url;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建人姓名(冗余)
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updateUserId;

    /**
     * 更新人姓名(冗余)
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    private Date updateTime;


}
