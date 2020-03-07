package com.permission.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级菜单id：0-当前菜单为父级菜单
     */
    private Integer pId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 前端路由路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单类型：10-菜单，20-按钮
     */
    private Integer type;

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
