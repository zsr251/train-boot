package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.LeaveApplyConvert;
import com.train.train.entity.LeaveApplyEntity;
import com.train.train.service.LeaveApplyService;
import com.train.train.query.LeaveApplyQuery;
import com.train.train.vo.LeaveApplyVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 请假申请
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/leave_apply")
@Tag(name="请假申请")
@AllArgsConstructor
public class LeaveApplyController {
    private final LeaveApplyService leaveApplyService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:leave_apply:page')")
    public Result<PageResult<LeaveApplyVO>> page(@ParameterObject @Valid LeaveApplyQuery query){
        PageResult<LeaveApplyVO> page = leaveApplyService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:leave_apply:info')")
    public Result<LeaveApplyVO> get(@PathVariable("id") Long id){
        LeaveApplyEntity entity = leaveApplyService.getById(id);

        return Result.ok(LeaveApplyConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:leave_apply:save')")
    public Result<String> save(@RequestBody LeaveApplyVO vo){
        leaveApplyService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:leave_apply:update')")
    public Result<String> update(@RequestBody @Valid LeaveApplyVO vo){
        leaveApplyService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:leave_apply:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        leaveApplyService.delete(idList);

        return Result.ok();
    }
}