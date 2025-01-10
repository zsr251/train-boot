package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.CourseVO;
import com.train.train.query.CourseQuery;
import com.train.train.entity.CourseEntity;

import java.util.List;

/**
 * 课程
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface CourseService extends BaseService<CourseEntity> {

    PageResult<CourseVO> page(CourseQuery query);

    void save(CourseVO vo);

    void update(CourseVO vo);

    void delete(List<Long> idList);

    List<String> getNameList(List<String> courseCodeList);

    CourseEntity getByCourseCode(String courseCode);
}