package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.CourseEnrollEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 课程报名
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseEnrollDao extends BaseDao<CourseEnrollEntity> {
	
}