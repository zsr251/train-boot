package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.ClassCourseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 班级课程
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ClassCourseDao extends BaseDao<ClassCourseEntity> {
	
}