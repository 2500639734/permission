package com.permission.dto;

import com.permission.pojo.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: shenke
 * @date: 2020/3/9 12:37
 * @description: 系统菜单返参Dto
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMenuDto extends SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型描述
     */
    private String typeDesc;

    /**
     * 是否选中: 10-选中，20-未选中
     */
    private Integer checked;

    /**
     * SysMenu -> SysMenuDto
     * @param sysMenu
     * @return
     */
    public static SysMenuDto toSysMenuDto (SysMenu sysMenu) {
        SysMenuDto sysMenuDto = new SysMenuDto();
        BeanUtils.copyProperties(sysMenu, sysMenuDto);
        return sysMenuDto;
    }

    /**
     * List<SysMenu> -> List<SysMenuDto>
     * @param sysMenuList
     * @return
     */
    public static List<SysMenuDto> toSysMenuDto (List<SysMenu> sysMenuList) {
        return sysMenuList.stream().map(sysMenu -> SysMenuDto.toSysMenuDto(sysMenu)).collect(Collectors.toList());
    }

}
