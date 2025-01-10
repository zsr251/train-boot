package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.service.ClassCourseService;
import com.train.train.query.ClassCourseQuery;
import com.train.train.vo.ClassCourseVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 班级课程
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/class_course")
@Tag(name="班级课程")
@AllArgsConstructor
public class ClassCourseController {
    private final ClassCourseService classCourseService;

    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<ClassCourseVO>> page(@ParameterObject @Valid ClassCourseQuery query){
        PageResult<ClassCourseVO> page = classCourseService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<ClassCourseVO> get(@PathVariable("id") Long id){
        ClassCourseVO vo = classCourseService.getVOById(id);

        return Result.ok(vo);
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody ClassCourseVO vo){
        classCourseService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody @Valid ClassCourseVO vo){
        classCourseService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    public Result<String> delete(@RequestBody List<Long> idList){
        classCourseService.delete(idList);

        return Result.ok();
    }

    @RepeatRequestLimit
    @PostMapping("scheduleCourse")
    @Operation(summary = "排课")
    public Result<String> scheduleCourse(@RequestBody ClassCourseVO vo){
        classCourseService.scheduleCourse(vo);

        return Result.ok();
    }
    @RepeatRequestLimit
    @PutMapping("resetSchedule/{id}")
    @Operation(summary = "重置排课")
    public Result<String> resetSchedule(@PathVariable("id") Long id){
        classCourseService.resetSchedule(id);

        return Result.ok();
    }
}