package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysLogLoginEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 *

 */
@Mapper
public interface SysLogLoginDao extends BaseDao<SysLogLoginEntity> {

}