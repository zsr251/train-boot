package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.CourseAuditionVO;
import com.train.train.query.CourseAuditionQuery;
import com.train.train.entity.CourseAuditionEntity;

import java.util.List;

/**
 * 课程试听
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface CourseAuditionService extends BaseService<CourseAuditionEntity> {

    PageResult<CourseAuditionVO> page(CourseAuditionQuery query);

    void save(CourseAuditionVO vo);

    void update(CourseAuditionVO vo);

    void delete(List<Long> idList);
}