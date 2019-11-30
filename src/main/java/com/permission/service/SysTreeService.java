package com.permission.service;

import com.permission.dto.SysDeptTreeDto;

import java.util.List;

/**
 * @auther: shenke
 * @date: 2019/11/1 20:37
 * @description: 树形结构Service
 */
public interface SysTreeService {

    /**
     * 获取部门树
     * @return
     */
    List<SysDeptTreeDto> getSysDeptTree ();

}
