package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.LessonStudentConvert;
import com.train.train.entity.LessonStudentEntity;
import com.train.train.service.LessonStudentService;
import com.train.train.query.LessonStudentQuery;
import com.train.train.vo.LessonStudentVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 课程计划学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/lesson_student")
@Tag(name="课程计划学员")
@AllArgsConstructor
public class LessonStudentController {
    private final LessonStudentService lessonStudentService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:lesson_student:page')")
    public Result<PageResult<LessonStudentVO>> page(@ParameterObject @Valid LessonStudentQuery query){
        PageResult<LessonStudentVO> page = lessonStudentService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:lesson_student:info')")
    public Result<LessonStudentVO> get(@PathVariable("id") Long id){
        LessonStudentEntity entity = lessonStudentService.getById(id);

        return Result.ok(LessonStudentConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:lesson_student:save')")
    public Result<String> save(@RequestBody LessonStudentVO vo){
        lessonStudentService.save(vo);

        return Result.ok();
    }

    @RepeatRequestLimit
    @PostMapping("addAudition")
    @Operation(summary = "添加试听")
    public Result<String> addAudition(@RequestBody LessonStudentVO vo){
        lessonStudentService.addAudition(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:lesson_student:update')")
    public Result<String> update(@RequestBody @Valid LessonStudentVO vo){
        lessonStudentService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:lesson_student:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        lessonStudentService.delete(idList);

        return Result.ok();
    }

    @GetMapping("lessonStudents")
    @Operation(summary = "课程学员列表")
    public Result<List<LessonStudentVO>> getLessonStudents(@RequestParam Long lessonId){
        List<LessonStudentVO> list = lessonStudentService.getLessonStudents(lessonId);

        return Result.ok(list);
    }
}