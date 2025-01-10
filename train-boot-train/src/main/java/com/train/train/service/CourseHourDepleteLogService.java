package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.CourseHourDepleteLogVO;
import com.train.train.query.CourseHourDepleteLogQuery;
import com.train.train.entity.CourseHourDepleteLogEntity;

import java.util.List;

/**
 * 消课记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface CourseHourDepleteLogService extends BaseService<CourseHourDepleteLogEntity> {

    PageResult<CourseHourDepleteLogVO> page(CourseHourDepleteLogQuery query);

    void save(CourseHourDepleteLogVO vo);

    void update(CourseHourDepleteLogVO vo);

    void delete(List<Long> idList);
}