package com.train.system.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysMailLogEntity;
import com.train.system.query.SysMailLogQuery;
import com.train.system.vo.SysMailLogVO;

import java.util.List;

/**
 * 邮件日志
 *
 */
public interface SysMailLogService extends BaseService<SysMailLogEntity> {

    PageResult<SysMailLogVO> page(SysMailLogQuery query);

    void delete(List<Long> idList);
}