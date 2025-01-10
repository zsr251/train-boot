package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.CourseHourDepleteLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 消课记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseHourDepleteLogDao extends BaseDao<CourseHourDepleteLogEntity> {
	
}