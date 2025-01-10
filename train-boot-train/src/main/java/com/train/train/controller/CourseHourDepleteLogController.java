package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.query.CourseQuery;
import com.train.train.vo.CourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.CourseHourDepleteLogConvert;
import com.train.train.entity.CourseHourDepleteLogEntity;
import com.train.train.service.CourseHourDepleteLogService;
import com.train.train.query.CourseHourDepleteLogQuery;
import com.train.train.vo.CourseHourDepleteLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 消课记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/course_hour_deplete_log")
@Tag(name="消课记录")
@AllArgsConstructor
public class CourseHourDepleteLogController {
    private final CourseHourDepleteLogService courseHourDepleteLogService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:course_hour_deplete_log:page')")
    public Result<PageResult<CourseHourDepleteLogVO>> page(@ParameterObject @Valid CourseHourDepleteLogQuery query){
        PageResult<CourseHourDepleteLogVO> page = courseHourDepleteLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:course_hour_deplete_log:info')")
    public Result<CourseHourDepleteLogVO> get(@PathVariable("id") Long id){
        CourseHourDepleteLogEntity entity = courseHourDepleteLogService.getById(id);

        return Result.ok(CourseHourDepleteLogConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:course_hour_deplete_log:save')")
    public Result<String> save(@RequestBody CourseHourDepleteLogVO vo){
        courseHourDepleteLogService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:course_hour_deplete_log:update')")
    public Result<String> update(@RequestBody @Valid CourseHourDepleteLogVO vo){
        courseHourDepleteLogService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:course_hour_deplete_log:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        courseHourDepleteLogService.delete(idList);

        return Result.ok();
    }
    @GetMapping("export")
    @Operation(summary = "导出课消记录")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject CourseHourDepleteLogQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, CourseHourDepleteLogVO.class, courseHourDepleteLogService, "page",CourseHourDepleteLogQuery.class, query,"课消记录", null,null);
    }
}