package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.CourseAuditionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 课程试听
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseAuditionDao extends BaseDao<CourseAuditionEntity> {
	
}