package com.permission.controller;


import com.permission.common.Result;
import com.permission.dto.input.SysDeptInput;
import com.permission.service.SysDeptService;
import com.permission.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author shenke
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/sys-dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    /**
     * 获取部门列表
     * @return
     */
    @GetMapping("/selectList")
    public Result selectList () {
        return Result.success(sysDeptService.selectDeptList());
    }

    /**
     * 保存部门信息
     * @param sysDeptInput
     * @return
     */
    @PostMapping("/save")
    public Result save (@RequestBody @Validated (value = SysDeptInput.Save.class) SysDeptInput sysDeptInput) {
        sysDeptService.saveDept(sysDeptInput);
        return Result.success();
    }

    /**
     * 更新部门信息
     * @param sysDeptInput
     * @return
     */
    @PostMapping("/update")
    public Result update (@RequestBody @Validated (value = SysDeptInput.Update.class) SysDeptInput sysDeptInput) {
        sysDeptService.updateDept(sysDeptInput);
        return Result.success();
    }

    /**
     * 获取部门树
     * @return
     */
    @GetMapping("/getSysDeptTree")
    public Result getSysDeptTree () {
        return Result.success(sysTreeService.getSysDeptTree());
    }

}
