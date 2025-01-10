package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.LeaveApplyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 请假申请
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface LeaveApplyDao extends BaseDao<LeaveApplyEntity> {
	
}