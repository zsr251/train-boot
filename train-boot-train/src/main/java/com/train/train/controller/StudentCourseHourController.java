package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.StudentCourseHourConvert;
import com.train.train.entity.StudentCourseHourEntity;
import com.train.train.service.StudentCourseHourService;
import com.train.train.query.StudentCourseHourQuery;
import com.train.train.vo.CourseHourDepleteVO;
import com.train.train.vo.StudentCourseHourVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

/**
 * 学员课程课时
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@RestController
@RequestMapping("train/student_course_hour")
@Tag(name = "学员课程课时")
@AllArgsConstructor
public class StudentCourseHourController {
    private final StudentCourseHourService studentCourseHourService;

    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<StudentCourseHourVO>> page(@ParameterObject @Valid StudentCourseHourQuery query) {
        PageResult<StudentCourseHourVO> page = studentCourseHourService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<StudentCourseHourVO> get(@PathVariable("id") Long id) {
        StudentCourseHourEntity entity = studentCourseHourService.getById(id);

        return Result.ok(StudentCourseHourConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:student_course_hour:save')")
    public Result<String> save(@RequestBody StudentCourseHourVO vo) {
        studentCourseHourService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:student_course_hour:update')")
    public Result<String> update(@RequestBody @Valid StudentCourseHourVO vo) {
        studentCourseHourService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:student_course_hour:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        studentCourseHourService.delete(idList);

        return Result.ok();
    }

    @GetMapping("student")
    @Operation(summary = "获取学员的课程")
    public Result<List<StudentCourseHourVO>> getByStudentId(@RequestParam Long studentId) {

        return Result.ok(studentCourseHourService.getByStudentId(studentId));
    }

    @RepeatRequestLimit
    @PostMapping("addDeplete")
    @Operation(summary = "单独消课")
    public Result<String> addDeplete(@RequestBody CourseHourDepleteVO depleteVO) {
        studentCourseHourService.depleteHour(depleteVO);

        return Result.ok();
    }

}