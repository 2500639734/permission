package com.permission.dto;

import com.permission.pojo.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @auther: shenke
 * @date: 2019/11/1 20:27
 * @description: 部门树形结构DTO
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDeptTreeDto extends SysDept implements Serializable, Comparable<SysDeptTreeDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 下级部门列表
     */
    private List<SysDeptTreeDto> childSysDeptTreeDtoList;

    /**
     * SysDept to SysDeptTreeDto
     * @param sysDept
     * @return
     */
    public static SysDeptTreeDto toSysDeptTreeDto(SysDept sysDept) {
        SysDeptTreeDto sysDeptTreeDto = new SysDeptTreeDto();
        BeanUtils.copyProperties(sysDept, sysDeptTreeDto);
        return sysDeptTreeDto;
    }

    /**
     * SysDeptTreeDto to SysDept
     * @param sysDeptTreeDto
     * @return
     */
    public static SysDept toSysDept (SysDeptTreeDto sysDeptTreeDto) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(sysDeptTreeDto, sysDept);
        return sysDept;
    }

    @Override
    public int compareTo(SysDeptTreeDto o) {
        return getSeq() - o.getSeq();
    }

}
