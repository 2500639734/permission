package com.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.permission.constant.CommonConstant;
import com.permission.dto.SysDeptTreeDto;
import com.permission.pojo.SysDept;
import com.permission.service.SysDeptService;
import com.permission.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: shenke
 * @date: 2019/11/1 20:38
 * @description: 树形结构构建Service
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public List<SysDeptTreeDto> getSysDeptTree () {
        List<SysDept> sysDeptList = sysDeptService.getSysDeptList();
        if (CollectionUtil.isEmpty(sysDeptList)) {
            return null;
        }

        // 获取部门列表
        List<SysDeptTreeDto> sysDeptTreeDtoList = CollUtil.newArrayList();
        sysDeptList.forEach(sysDept -> {
            SysDeptTreeDto sysDeptTreeDto = SysDeptTreeDto.toSysDeptTreeDto(sysDept);
            sysDeptTreeDtoList.add(sysDeptTreeDto);
        });

        // 获取顶级部门列表并按seq升序排序
        List<SysDeptTreeDto> parentSysDeptTreeDtoList = sysDeptTreeDtoList.stream()
                .filter(sysDeptTreeDto -> CommonConstant.ROOT.equals(sysDeptTreeDto.getParentId()))
                .sorted(SysDeptTreeDto::compareTo)
                .collect(Collectors.toList());

        // 构建部门树
        return buildSysDeptDtoTree(sysDeptTreeDtoList, parentSysDeptTreeDtoList);
    }

    /**
     * 构建部门树
     * @param sysDeptTreeDtoList
     * @param parentSysDeptTreeDtoList
     * @return
     */
    private List<SysDeptTreeDto> buildSysDeptDtoTree (List<SysDeptTreeDto> sysDeptTreeDtoList, List<SysDeptTreeDto> parentSysDeptTreeDtoList) {
        parentSysDeptTreeDtoList.forEach(parentSysDeptTreeDto -> {
            List<SysDeptTreeDto> childSysDeptDtoList = CollUtil.newArrayList();
            // 获取下级部门并按seq升序排序
            sysDeptTreeDtoList.forEach(sysDeptTreeDto -> {
                if (sysDeptTreeDto.getParentId().equals(parentSysDeptTreeDto.getId())) {
                    childSysDeptDtoList.add(sysDeptTreeDto);
                }
            });
            childSysDeptDtoList.sort(SysDeptTreeDto::compareTo);

            // 递归设置下级部门
            parentSysDeptTreeDto.setChildSysDeptTreeDtoList(buildSysDeptDtoTree(sysDeptTreeDtoList, childSysDeptDtoList));
        });
        return parentSysDeptTreeDtoList;
    }

}
