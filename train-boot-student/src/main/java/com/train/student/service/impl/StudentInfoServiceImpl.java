package com.train.student.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.train.framework.common.exception.ErrorCode;
import com.train.framework.common.exception.ServerException;
import com.train.student.service.StudentInfoService;
import com.train.student.vo.LessonInfoVO;
import com.train.student.vo.StudentCourseInfoVO;
import com.train.train.constant.TrainConstant;
import com.train.train.query.LessonTimetableQuery;
import com.train.train.query.TimetableQuery;
import com.train.train.service.LessonStudentService;
import com.train.train.service.LessonTimetableService;
import com.train.train.service.StudentCourseHourService;
import com.train.train.service.StudentService;
import com.train.train.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentInfoServiceImpl implements StudentInfoService {
    private final StudentService studentService;
    private final LessonTimetableService lessonTimetableService;
    private final LessonStudentService lessonStudentService;
    private final StudentCourseHourService studentCourseHourService;

    @Override
    public List<LessonTimetableVO> getTodayLesson(Long studentId) {
        return lessonTimetableService.today(new LessonTimetableQuery().setStudentId(studentId));
    }

    @Override
    public LessonInfoVO getLessonInfo(Long studentId, Long lessonId) {
        LessonTimetableVO timetableVO = lessonTimetableService.getVOById(lessonId);
        if (timetableVO == null) {
            throw new ServerException("未找到课程");
        }
        List<LessonStudentVO> studentVOList = lessonStudentService.getLessonStudents(lessonId)
                .stream().filter(item -> item.getStudentId().equals(studentId)).toList();
        LessonInfoVO vo = BeanUtil.copyProperties(timetableVO, LessonInfoVO.class);
        vo.setStudentList(studentVOList);
        return vo;
    }

    @Override
    public LessonTimetableAppointShowVO getTimetable(Long studentId, Integer week) {

        return lessonTimetableService.getAppointTimetable(new TimetableQuery().setWeek(week).setStudentId(studentId));
    }

    @Override
    public List<StudentCourseInfoVO> getStudentCourse(Long studentId) {
        List<StudentCourseHourVO> hourVOList = studentCourseHourService.getByStudentId(studentId);
        List<StudentCourseInfoVO> voList = BeanUtil.copyToList(hourVOList, StudentCourseInfoVO.class);
        return voList;
    }
}
