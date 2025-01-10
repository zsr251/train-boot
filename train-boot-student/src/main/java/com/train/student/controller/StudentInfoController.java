package com.train.student.controller;

import com.train.framework.common.utils.Result;
import com.train.framework.security.utils.TokenUtils;
import com.train.student.service.AuthService;
import com.train.student.service.StudentInfoService;
import com.train.student.vo.LessonInfoVO;
import com.train.student.vo.StudentCourseInfoVO;
import com.train.train.query.TimetableQuery;
import com.train.train.vo.LessonTimetableAppointShowVO;
import com.train.train.vo.LessonTimetableVO;
import com.train.train.vo.StudentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("student")
@Tag(name = "学员")
@AllArgsConstructor
public class StudentInfoController {
    private final StudentInfoService studentService;
    private final AuthService authService;
    @GetMapping("info")
    @Operation(summary = "学员信息")
    public Result<StudentVO> studentInfo(HttpServletRequest request) {

        return Result.ok(authService.checkToken(TokenUtils.getAccessToken(request)));
    }

    @GetMapping("today")
    @Operation(summary = "今天课程")
    public Result<List<LessonTimetableVO>> today(HttpServletRequest request) {
        StudentVO student = authService.checkToken(TokenUtils.getAccessToken(request));
        return Result.ok(studentService.getTodayLesson(student.getId()));
    }

    @GetMapping("lesson/{id}")
    @Operation(summary = "课程详情")
    public Result<LessonInfoVO> lesson(@PathVariable("id") Long id, HttpServletRequest request) {
        StudentVO student = authService.checkToken(TokenUtils.getAccessToken(request));

        return Result.ok(studentService.getLessonInfo(student.getId(), id));
    }

    @GetMapping("timetable")
    @Operation(summary = "单学生课表")
    public Result<LessonTimetableAppointShowVO> getTimetable(@ParameterObject TimetableQuery query, HttpServletRequest request) {
        StudentVO student = authService.checkToken(TokenUtils.getAccessToken(request));
        return Result.ok(studentService.getTimetable(student.getId(), query.getWeek()));
    }

    @GetMapping("course")
    @Operation(summary = "学生课程")
    public Result<List<StudentCourseInfoVO>> getStudentCourse(HttpServletRequest request) {
        StudentVO student = authService.checkToken(TokenUtils.getAccessToken(request));
        return Result.ok(studentService.getStudentCourse(student.getId()));
    }

}
