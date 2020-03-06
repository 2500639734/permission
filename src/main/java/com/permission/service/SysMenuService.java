package com.permission.service;

import com.permission.dto.SysMenuTree;
import com.permission.pojo.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author shenke
 * @since 2020-03-06
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户包含的菜单列表
     * @param userId 用户id
     * @return
     */
    List<SysMenu> selectMenuListByUserId (Integer userId);

    /**
     * 获取用户包含的菜单列表,树形结构
     * @param userId 用户id
     * @return
     */
    List<SysMenuTree> selectMenuTreeByUserId(Integer userId);

}
