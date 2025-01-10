package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysSmsLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信日志
 *

 */
@Mapper
public interface SysSmsLogDao extends BaseDao<SysSmsLogEntity> {

}