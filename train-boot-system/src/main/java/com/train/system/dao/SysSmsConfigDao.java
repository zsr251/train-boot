package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysSmsConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信配置
 *

 */
@Mapper
public interface SysSmsConfigDao extends BaseDao<SysSmsConfigEntity> {

}