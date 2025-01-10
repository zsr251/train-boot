package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.CourseAuditionConvert;
import com.train.train.entity.CourseAuditionEntity;
import com.train.train.service.CourseAuditionService;
import com.train.train.query.CourseAuditionQuery;
import com.train.train.vo.CourseAuditionVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 课程试听
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/course_audition")
@Tag(name="课程试听")
@AllArgsConstructor
public class CourseAuditionController {
    private final CourseAuditionService courseAuditionService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:course_audition:page')")
    public Result<PageResult<CourseAuditionVO>> page(@ParameterObject @Valid CourseAuditionQuery query){
        PageResult<CourseAuditionVO> page = courseAuditionService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:course_audition:info')")
    public Result<CourseAuditionVO> get(@PathVariable("id") Long id){
        CourseAuditionEntity entity = courseAuditionService.getById(id);

        return Result.ok(CourseAuditionConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:course_audition:save')")
    public Result<String> save(@RequestBody CourseAuditionVO vo){
        courseAuditionService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:course_audition:update')")
    public Result<String> update(@RequestBody @Valid CourseAuditionVO vo){
        courseAuditionService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:course_audition:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        courseAuditionService.delete(idList);

        return Result.ok();
    }
}