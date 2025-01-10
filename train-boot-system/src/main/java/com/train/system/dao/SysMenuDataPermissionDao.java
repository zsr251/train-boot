package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysMenuDataPermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 数据权限
*
* @author jasonzhu 
* @since 1.0.0 2024-12-06
*/
@Mapper
public interface SysMenuDataPermissionDao extends BaseDao<SysMenuDataPermissionEntity> {
    /**
     * 根据用户id和前端组件名称查询数据权限
     * @param userId 用户ID
     * @param componentList 组件列表
     * @return
     */
	List<SysMenuDataPermissionEntity> getListByUserId(@Param("userId") Long userId,@Param("componentList") List<String> componentList);

    /**
     * 根据角色ID和前端组件名称查询数据权限
     * @param roleId 角色ID
     * @param component 组件
     * @return
     */
    List<SysMenuDataPermissionEntity> getListByRoleId(@Param("roleId") Long roleId,@Param("component") String component);

    /**
     * 根据前端组件名称查询数据权限
     * @param component
     * @return
     */
    List<SysMenuDataPermissionEntity> getListByComponent(@Param("component") String component);

}