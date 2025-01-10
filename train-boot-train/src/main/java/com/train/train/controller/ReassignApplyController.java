package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.ReassignApplyConvert;
import com.train.train.entity.ReassignApplyEntity;
import com.train.train.service.ReassignApplyService;
import com.train.train.query.ReassignApplyQuery;
import com.train.train.vo.ReassignApplyVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 调课申请
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/reassign_apply")
@Tag(name="调课申请")
@AllArgsConstructor
public class ReassignApplyController {
    private final ReassignApplyService reassignApplyService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:reassign_apply:page')")
    public Result<PageResult<ReassignApplyVO>> page(@ParameterObject @Valid ReassignApplyQuery query){
        PageResult<ReassignApplyVO> page = reassignApplyService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:reassign_apply:info')")
    public Result<ReassignApplyVO> get(@PathVariable("id") Long id){
        ReassignApplyEntity entity = reassignApplyService.getById(id);

        return Result.ok(ReassignApplyConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:reassign_apply:save')")
    public Result<String> save(@RequestBody ReassignApplyVO vo){
        reassignApplyService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:reassign_apply:update')")
    public Result<String> update(@RequestBody @Valid ReassignApplyVO vo){
        reassignApplyService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:reassign_apply:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        reassignApplyService.delete(idList);

        return Result.ok();
    }
}