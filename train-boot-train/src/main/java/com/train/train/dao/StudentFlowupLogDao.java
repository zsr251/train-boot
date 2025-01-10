package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.StudentFlowupLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 跟进记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface StudentFlowupLogDao extends BaseDao<StudentFlowupLogEntity> {
	
}