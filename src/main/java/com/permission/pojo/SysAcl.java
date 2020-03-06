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
     * 创建人id
     */
    private Integer createUserId;

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
    private Integer updateUserId;

    /**
     * 更新人姓名(冗余)
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    private Date updateTime;


}
