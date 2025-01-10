package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.CourseHourBuyLogConvert;
import com.train.train.entity.CourseHourBuyLogEntity;
import com.train.train.service.CourseHourBuyLogService;
import com.train.train.query.CourseHourBuyLogQuery;
import com.train.train.vo.CourseHourBuyLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 课时购买记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/course_hour_buy_log")
@Tag(name="课时购买记录")
@AllArgsConstructor
public class CourseHourBuyLogController {
    private final CourseHourBuyLogService courseHourBuyLogService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:course_hour_buy_log:page')")
    public Result<PageResult<CourseHourBuyLogVO>> page(@ParameterObject @Valid CourseHourBuyLogQuery query){
        PageResult<CourseHourBuyLogVO> page = courseHourBuyLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:course_hour_buy_log:info')")
    public Result<CourseHourBuyLogVO> get(@PathVariable("id") Long id){
        CourseHourBuyLogEntity entity = courseHourBuyLogService.getById(id);

        return Result.ok(CourseHourBuyLogConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:course_hour_buy_log:save')")
    public Result<String> save(@RequestBody CourseHourBuyLogVO vo){
        courseHourBuyLogService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:course_hour_buy_log:update')")
    public Result<String> update(@RequestBody @Valid CourseHourBuyLogVO vo){
        courseHourBuyLogService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:course_hour_buy_log:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        courseHourBuyLogService.delete(idList);

        return Result.ok();
    }
}