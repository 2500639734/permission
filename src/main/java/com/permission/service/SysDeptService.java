package com.permission.service;

import com.permission.dto.input.SysDeptInput;
import com.permission.pojo.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
public interface SysDeptService extends IService<SysDept> {

    List<SysDept> selectDeptList ();

    /**
     * 保存部门信息
     * @param sysDeptInput
     */
    boolean saveDept (SysDeptInput sysDeptInput);

    /**
     * 更新部门信息
     * @param sysDeptInput
     */
    boolean updateDept (SysDeptInput sysDeptInput);

    /**
     * 获取部门信息
     * @param id
     * @return
     */
    SysDept getSysDeptById (Long id);

    /**
     * 获取部门列表
     * @return
     */
    List<SysDept> getSysDeptList ();

    /**
     * 校验同一层级下是否存在名称相同的部门
     * @param parentId
     * @param deptName
     * @return
     */
    boolean checkDeptNameSomeExist (Long parentId, String deptName);

}
