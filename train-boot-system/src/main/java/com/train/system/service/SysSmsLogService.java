package com.train.system.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysSmsLogEntity;
import com.train.system.query.SysSmsLogQuery;
import com.train.system.vo.SysSmsLogVO;

import java.util.List;

/**
 * 短信日志
 *
 */
public interface SysSmsLogService extends BaseService<SysSmsLogEntity> {

    PageResult<SysSmsLogVO> page(SysSmsLogQuery query);

    void delete(List<Long> idList);
}