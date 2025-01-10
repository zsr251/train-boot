package com.train.quartz.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.quartz.entity.ScheduleJobEntity;
import com.train.quartz.query.ScheduleJobQuery;
import com.train.quartz.vo.ScheduleJobVO;

import java.util.List;

/**
 * 定时任务
 *

 */
public interface ScheduleJobService extends BaseService<ScheduleJobEntity> {

    PageResult<ScheduleJobVO> page(ScheduleJobQuery query);

    void save(ScheduleJobVO vo);

    void update(ScheduleJobVO vo);

    void delete(List<Long> idList);

    void run(ScheduleJobVO vo);

    void changeStatus(ScheduleJobVO vo);
}