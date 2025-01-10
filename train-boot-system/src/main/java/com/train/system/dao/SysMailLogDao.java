package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysMailLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件日志
 *

 */
@Mapper
public interface SysMailLogDao extends BaseDao<SysMailLogEntity> {

}