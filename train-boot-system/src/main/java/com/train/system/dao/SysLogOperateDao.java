package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysLogOperateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *

 */
@Mapper
public interface SysLogOperateDao extends BaseDao<SysLogOperateEntity> {

}