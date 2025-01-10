package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.entity.ClassCourseEntity;
import com.train.train.query.TimetableQuery;
import com.train.train.vo.LessonNamedVO;
import com.train.train.vo.LessonTimetableAppointShowVO;
import com.train.train.vo.LessonTimetableGroupShowVO;
import com.train.train.vo.LessonTimetableVO;
import com.train.train.vo.*;
import com.train.train.query.LessonTimetableQuery;
import com.train.train.entity.LessonTimetableEntity;

import java.util.List;

/**
 * 课程计划
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
public interface LessonTimetableService extends BaseService<LessonTimetableEntity> {

    PageResult<LessonTimetableVO> page(LessonTimetableQuery query);

    List<LessonTimetableVO> today(LessonTimetableQuery query);

    LessonTimetableVO getVOById(Long id);

    void save(LessonTimetableVO vo);

    void update(LessonTimetableVO vo);

    void delete(List<Long> idList);

    Integer scheduleCourse(Long classCourseId);

    Integer scheduleCourse(ClassCourseEntity classCourse);

    Integer scheduleReset(Long classCourseId);

    LessonTimetableGroupShowVO getTeachersTimetable(Integer week);

    LessonTimetableGroupShowVO getClassroomsTimetable(Integer week);

    LessonTimetableAppointShowVO getAppointTimetable(TimetableQuery query);

    void lessonNamed(LessonNamedVO vo);

    void lessonReassign(LessonTimetableVO vo);

    void lessonCancel(Long id);
}