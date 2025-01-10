package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysMenuDataPermissionEntity;
import com.train.system.entity.SysRoleDataPermissionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 数据权限
*
* @author jasonzhu 
* @since 1.0.0 2024-12-06
*/
@Mapper
public interface SysRoleDataPermissionDao extends BaseDao<SysRoleDataPermissionEntity> {
	
}