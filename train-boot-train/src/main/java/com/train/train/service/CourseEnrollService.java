package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.CourseEnrollVO;
import com.train.train.query.CourseEnrollQuery;
import com.train.train.entity.CourseEnrollEntity;

import java.util.List;

/**
 * 课程报名
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface CourseEnrollService extends BaseService<CourseEnrollEntity> {

    PageResult<CourseEnrollVO> page(CourseEnrollQuery query);

    void save(CourseEnrollVO vo);

    void update(CourseEnrollVO vo);

    void delete(List<Long> idList);

    void updateEnrollStatus(Long id, String enrollStatus);
}