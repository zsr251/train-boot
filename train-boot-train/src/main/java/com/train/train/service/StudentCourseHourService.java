package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.CourseHourDepleteVO;
import com.train.train.vo.StudentCourseHourVO;
import com.train.train.query.StudentCourseHourQuery;
import com.train.train.entity.StudentCourseHourEntity;

import java.util.List;

/**
 * 学员课程课时
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface StudentCourseHourService extends BaseService<StudentCourseHourEntity> {

    PageResult<StudentCourseHourVO> page(StudentCourseHourQuery query);

    void save(StudentCourseHourVO vo);

    void update(StudentCourseHourVO vo);

    void delete(List<Long> idList);

    List<StudentCourseHourVO> getByStudentId(Long studentId);

    void depleteHour(CourseHourDepleteVO depleteVO);
}