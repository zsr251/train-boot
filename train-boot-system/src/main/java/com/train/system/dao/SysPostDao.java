package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysPostEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 岗位管理
*
*  
*/
@Mapper
public interface SysPostDao extends BaseDao<SysPostEntity> {
	
}