package com.permission.dto.input;

import com.permission.pojo.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: shenke
 * @date: 2020/2/21 22:20
 * @description: 系统用户信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class SysUserInfo implements Serializable {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户编码
     */
    private String code;

    /**
     * 用户名
     */
    private String username;

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

    /**
     * SysUser -> SysUserInfo
     * @param sysUser
     * @return
     */
    public static SysUserInfo toSysUserInfo (SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }

        SysUserInfo sysUserInfo = new SysUserInfo();
        BeanUtils.copyProperties(sysUser, sysUserInfo);
        return sysUserInfo;
    }

}
