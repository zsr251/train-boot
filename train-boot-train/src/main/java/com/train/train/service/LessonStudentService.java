package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.LessonStudentVO;
import com.train.train.query.LessonStudentQuery;
import com.train.train.entity.LessonStudentEntity;

import java.util.List;

/**
 * 课程计划学员
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
public interface LessonStudentService extends BaseService<LessonStudentEntity> {

    PageResult<LessonStudentVO> page(LessonStudentQuery query);

    void save(LessonStudentVO vo);

    void addAudition(LessonStudentVO vo);

    void update(LessonStudentVO vo);

    void delete(List<Long> idList);

    List<LessonStudentVO> getLessonStudents(Long lessonId);
}