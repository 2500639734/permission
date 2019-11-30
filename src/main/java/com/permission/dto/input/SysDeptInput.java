package com.permission.dto.input;

import com.permission.constant.CommonConstant;
import com.permission.constant.DeptConstant;
import com.permission.pojo.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @auther: shenke
 * @date: 2019/10/30 21:11
 * @description: 保存/更新部门信息相关操作输入Dto
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDeptInput implements Serializable {

    /**
     * 新增时校验分组
     */
    public interface Save {

    }

    /**
     * 更新时校验分组
     */
    public interface Update {

    }

    /**
     * 部门id
     */
    @NotNull(message = DeptConstant.DEPT_ID_IS_NULL, groups = Update.class)
    private Long id;

    /**
     * 部门名称 : {
     *     不能为null,
     *     长度2-15之间
     * }
     */
    @NotBlank(message = DeptConstant.DEPT_NAME_IS_NULL)
    @Length(min = 2, max = 15, message = DeptConstant.DEPT_NAME_LENGTH_ERROR, groups = {Save.class, Update.class})
    private String name;

    /**
     * 上级部门id
     */
    @NotNull(message = DeptConstant.DEPT_PARENT_ID_IS_NULL, groups = {Save.class, Update.class})
    private Long parentId = 0L;

    /**
     * 部门在当前层级下的顺序,升序
     */
    @NotNull(message = DeptConstant.DEPT_SQL_IS_NULL, groups = {Save.class, Update.class})
    private Integer seq;

    /**
     * 备注 : {
     *      长度不能超过200
     * }
     */
    @Length(max = 200, message = CommonConstant.REMARK_LENGTH_ERROR)
    private String remark;

    /**
     * SysDeptInput to SysDept
     * @param sysDeptInput
     * @return
     */
    public static SysDept toSysDept (SysDeptInput sysDeptInput) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptInput, sysDept);
        return sysDept;
    }

    /**
     * SysDept to SysDeptInput
     * @param sysDept
     * @return
     */
    public static SysDeptInput toSysDeptInput (SysDept sysDept) {
        SysDeptInput sysDeptInput = new SysDeptInput();
        BeanUtils.copyProperties(sysDept, sysDeptInput);
        return sysDeptInput;
    }

}
