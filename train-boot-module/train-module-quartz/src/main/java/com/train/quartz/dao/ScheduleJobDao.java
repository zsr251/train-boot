package com.train.quartz.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.quartz.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 定时任务
*
*  
*/
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
}