package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.query.OrderQuery;
import com.train.train.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.TeacherSettlementConvert;
import com.train.train.entity.TeacherSettlementEntity;
import com.train.train.service.TeacherSettlementService;
import com.train.train.query.TeacherSettlementQuery;
import com.train.train.vo.TeacherSettlementVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 员工工资条
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/teacher_settlement")
@Tag(name="员工工资条")
@AllArgsConstructor
public class TeacherSettlementController {
    private final TeacherSettlementService teacherSettlementService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:teacher_settlement:page')")
    public Result<PageResult<TeacherSettlementVO>> page(@ParameterObject @Valid TeacherSettlementQuery query){
        PageResult<TeacherSettlementVO> page = teacherSettlementService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:teacher_settlement:info')")
    public Result<TeacherSettlementVO> get(@PathVariable("id") Long id){
        TeacherSettlementEntity entity = teacherSettlementService.getById(id);

        return Result.ok(TeacherSettlementConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:teacher_settlement:save')")
    public Result<String> save(@RequestBody TeacherSettlementVO vo){
        teacherSettlementService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:teacher_settlement:update')")
    public Result<String> update(@RequestBody @Valid TeacherSettlementVO vo){
        teacherSettlementService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:teacher_settlement:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        teacherSettlementService.delete(idList);

        return Result.ok();
    }

    @GetMapping("export")
    @Operation(summary = "导出工资结算")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject TeacherSettlementQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, TeacherSettlementVO.class, teacherSettlementService, "page",TeacherSettlementQuery.class, query,"工资结算", null,null);
    }
}