package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.LessonStudentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 课程计划学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface LessonStudentDao extends BaseDao<LessonStudentEntity> {
	
}