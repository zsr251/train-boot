package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.CourseHourBuyLogVO;
import com.train.train.query.CourseHourBuyLogQuery;
import com.train.train.entity.CourseHourBuyLogEntity;

import java.util.List;

/**
 * 课时购买记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface CourseHourBuyLogService extends BaseService<CourseHourBuyLogEntity> {

    PageResult<CourseHourBuyLogVO> page(CourseHourBuyLogQuery query);

    void save(CourseHourBuyLogVO vo);

    void update(CourseHourBuyLogVO vo);

    void delete(List<Long> idList);
}