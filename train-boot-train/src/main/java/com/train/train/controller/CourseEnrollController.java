package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.CourseEnrollConvert;
import com.train.train.entity.CourseEnrollEntity;
import com.train.train.service.CourseEnrollService;
import com.train.train.query.CourseEnrollQuery;
import com.train.train.vo.CourseEnrollVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 课程报名
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/course_enroll")
@Tag(name="课程报名")
@AllArgsConstructor
public class CourseEnrollController {
    private final CourseEnrollService courseEnrollService;

    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<CourseEnrollVO>> page(@ParameterObject @Valid CourseEnrollQuery query){
        PageResult<CourseEnrollVO> page = courseEnrollService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<CourseEnrollVO> get(@PathVariable("id") Long id){
        CourseEnrollEntity entity = courseEnrollService.getById(id);

        return Result.ok(CourseEnrollConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody CourseEnrollVO vo){
        courseEnrollService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody @Valid CourseEnrollVO vo){
        courseEnrollService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    public Result<String> delete(@RequestBody List<Long> idList){
        courseEnrollService.delete(idList);

        return Result.ok();
    }

}