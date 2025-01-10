package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.convert.ClassInfoConvert;
import com.train.train.entity.ClassInfoEntity;
import com.train.train.query.ClassInfoQuery;
import com.train.train.service.ClassInfoService;
import com.train.train.vo.ClassInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@RestController
@RequestMapping("train/class_info")
@Tag(name = "班级")
@AllArgsConstructor
public class ClassInfoController {
    private final ClassInfoService classInfoService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:class_info:page')")
    public Result<PageResult<ClassInfoVO>> page(@ParameterObject @Valid ClassInfoQuery query) {
        PageResult<ClassInfoVO> page = classInfoService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:class_info:info')")
    public Result<ClassInfoVO> get(@PathVariable("id") Long id) {
        ClassInfoEntity entity = classInfoService.getById(id);

        return Result.ok(ClassInfoConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:class_info:save')")
    public Result<String> save(@RequestBody ClassInfoVO vo) {
        classInfoService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:class_info:update')")
    public Result<String> update(@RequestBody @Valid ClassInfoVO vo) {
        classInfoService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:class_info:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        classInfoService.delete(idList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public Result<List<String>> nameList(@RequestBody List<String> classCodeList) {
        List<String> list = classInfoService.getNameList(classCodeList);

        return Result.ok(list);
    }

    @GetMapping("export")
    @Operation(summary = "导出班级")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject ClassInfoQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, ClassInfoVO.class, classInfoService, "page", ClassInfoQuery.class, query, "班级", null, null);
    }
}