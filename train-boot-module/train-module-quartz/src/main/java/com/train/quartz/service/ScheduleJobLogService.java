package com.train.quartz.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.quartz.entity.ScheduleJobLogEntity;
import com.train.quartz.query.ScheduleJobLogQuery;
import com.train.quartz.vo.ScheduleJobLogVO;

/**
 * 定时任务日志
 *

 */
public interface ScheduleJobLogService extends BaseService<ScheduleJobLogEntity> {

    PageResult<ScheduleJobLogVO> page(ScheduleJobLogQuery query);

}