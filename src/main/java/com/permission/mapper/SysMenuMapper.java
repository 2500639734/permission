package com.permission.mapper;

import com.permission.pojo.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取用户包含的菜单列表
     * @param userId 用户id
     * @return
     */
    List<SysMenu> selectMenuListByUserId (@Param("userId") Integer userId);

}
