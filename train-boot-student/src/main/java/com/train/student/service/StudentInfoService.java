package com.train.student.service;

import com.train.student.vo.LessonInfoVO;
import com.train.student.vo.StudentCourseInfoVO;
import com.train.train.vo.LessonTimetableAppointShowVO;
import com.train.train.vo.LessonTimetableVO;
import com.train.train.vo.StudentCourseHourVO;
import com.train.train.vo.StudentVO;

import java.util.List;

public interface StudentInfoService {
    List<LessonTimetableVO> getTodayLesson(Long studentId);

    LessonInfoVO getLessonInfo(Long studentId, Long lessonId);

    LessonTimetableAppointShowVO getTimetable(Long studentId, Integer week);

    List<StudentCourseInfoVO> getStudentCourse(Long studentId);
}
