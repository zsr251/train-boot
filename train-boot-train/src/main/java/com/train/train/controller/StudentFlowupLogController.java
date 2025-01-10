package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.StudentFlowupLogConvert;
import com.train.train.entity.StudentFlowupLogEntity;
import com.train.train.service.StudentFlowupLogService;
import com.train.train.query.StudentFlowupLogQuery;
import com.train.train.vo.StudentFlowupLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 跟进记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/student_flowup_log")
@Tag(name="跟进记录")
@AllArgsConstructor
public class StudentFlowupLogController {
    private final StudentFlowupLogService studentFlowupLogService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:student_flowup_log:page')")
    public Result<PageResult<StudentFlowupLogVO>> page(@ParameterObject @Valid StudentFlowupLogQuery query){
        PageResult<StudentFlowupLogVO> page = studentFlowupLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:student_flowup_log:info')")
    public Result<StudentFlowupLogVO> get(@PathVariable("id") Long id){
        StudentFlowupLogEntity entity = studentFlowupLogService.getById(id);

        return Result.ok(StudentFlowupLogConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:student_flowup_log:save')")
    public Result<String> save(@RequestBody StudentFlowupLogVO vo){
        studentFlowupLogService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:student_flowup_log:update')")
    public Result<String> update(@RequestBody @Valid StudentFlowupLogVO vo){
        studentFlowupLogService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:student_flowup_log:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        studentFlowupLogService.delete(idList);

        return Result.ok();
    }
}