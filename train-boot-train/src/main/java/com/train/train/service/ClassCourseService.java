package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.ClassCourseVO;
import com.train.train.query.ClassCourseQuery;
import com.train.train.entity.ClassCourseEntity;

import java.util.List;

/**
 * 班级课程
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface ClassCourseService extends BaseService<ClassCourseEntity> {

    PageResult<ClassCourseVO> page(ClassCourseQuery query);

    void save(ClassCourseVO vo);

    void update(ClassCourseVO vo);

    void delete(List<Long> idList);

    ClassCourseVO getVOById(Long id);

    void scheduleCourse(ClassCourseVO vo);

    void scheduleCourse(Long classCourseId);

    void resetSchedule(Long classCourseId);
}