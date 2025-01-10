package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.LessonTimetableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* 课程计划
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface LessonTimetableDao extends BaseDao<LessonTimetableEntity> {
    /**
     * 根据班级课程id和课时查询课时对应的课时学时
     */
    @Select("select ifnull(sum(course_hour),0) from tt_lesson_timetable where class_course_id = #{classCourseId} and lesson_status = 'done' and deleted = 0")
	Integer getLessonDoneCourseHour(Long classCourseId);

    @Select("select ifnull(sum(course_hour),0) from tt_lesson_timetable where class_course_id = #{classCourseId} and lesson_status in ('wait','done') and deleted = 0")
    Integer getLessonExistCourseHour(Long classCourseId);
    /**
     * 获取用于排课的课程计划
     */
    List<LessonTimetableEntity> getTimetableForSchedule(@Param("dateList") List<Date> dateList, @Param( "classCode") String classCode, @Param("classroomCode") String classroomCode, @Param("teacherCode") String teacherCode);

}