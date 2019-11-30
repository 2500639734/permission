package com.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.permission.dto.input.SysDeptInput;
import com.permission.enumeration.ResultEnum;
import com.permission.exception.BusinessException;
import com.permission.pojo.SysDept;
import com.permission.mapper.SysDeptMapper;
import com.permission.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.permission.util.ValidatedUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> selectDeptList() {
        return sysDeptMapper.selectList(new QueryWrapper<>());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean saveDept (@Validated SysDeptInput sysDeptInput) {
        // 同一层级不允许出现两个名称相同相同的部门
        if (checkDeptNameSomeExist(sysDeptInput.getParentId(), sysDeptInput.getName())) {
            throw new BusinessException(ResultEnum.DEPT_NAME_SAME, sysDeptInput.getParentId(), sysDeptInput.getName());
        }

        // 保存部门信息
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptInput, sysDept);
        // TODO 后续补充真实ip及操作人信息
        sysDept.setOperator("system");
        sysDept.setOperatorIp("127.0.0.1");
        sysDept.setOperatorTime(new Date());
        return sysDeptMapper.insert(sysDept) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean updateDept(@Validated SysDeptInput sysDeptInput) {
        SysDept oldSysDept = sysDeptMapper.selectById(sysDeptInput.getId());
        ValidatedUtils.objectIsNuLL(oldSysDept, ResultEnum.DEPT_NOT_EXISTS, sysDeptInput.getId());

        // 如果修改了名称,校验同一层级不允许出现两个名称相同相同的部门
        if (!sysDeptInput.getName().equals(oldSysDept.getName())) {
            if (checkDeptNameSomeExist(sysDeptInput.getParentId(), sysDeptInput.getName())) {
                throw new BusinessException(ResultEnum.DEPT_NAME_SAME, sysDeptInput.getParentId(), sysDeptInput.getName());
            }
        }

        SysDept sysDept = SysDeptInput.toSysDept(sysDeptInput);
        // TODO 后续补充真实ip及操作人信息
        sysDept.setOperator("system-update");
        sysDept.setOperatorIp("127.0.0.1");
        sysDept.setOperatorTime(new Date());
        return sysDeptMapper.updateById(sysDept) > 0;
    }

    @Override
    public SysDept getSysDeptById (Long deptId) {
        return sysDeptMapper.selectById(deptId);
    }

    @Override
    public List<SysDept> getSysDeptList() {
        return sysDeptMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public boolean checkDeptNameSomeExist (Long parentId, String deptName) {
        return sysDeptMapper.selectCount(new QueryWrapper<SysDept>()
                .eq("parent_id", parentId)
                .eq("name", deptName)) > 0;
    }

}
