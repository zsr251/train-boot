package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.StudentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface StudentDao extends BaseDao<StudentEntity> {
	
}