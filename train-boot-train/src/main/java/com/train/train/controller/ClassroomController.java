package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.query.TeacherQuery;
import com.train.train.vo.TeacherVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.ClassroomConvert;
import com.train.train.entity.ClassroomEntity;
import com.train.train.service.ClassroomService;
import com.train.train.query.ClassroomQuery;
import com.train.train.vo.ClassroomVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 教室
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/classroom")
@Tag(name="教室")
@AllArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:classroom:page')")
    public Result<PageResult<ClassroomVO>> page(@ParameterObject @Valid ClassroomQuery query){
        PageResult<ClassroomVO> page = classroomService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:classroom:info')")
    public Result<ClassroomVO> get(@PathVariable("id") Long id){
        ClassroomEntity entity = classroomService.getById(id);

        return Result.ok(ClassroomConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:classroom:save')")
    public Result<String> save(@RequestBody ClassroomVO vo){
        classroomService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:classroom:update')")
    public Result<String> update(@RequestBody @Valid ClassroomVO vo){
        classroomService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:classroom:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        classroomService.delete(idList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public Result<List<String>> nameList(@RequestBody List<String> classroomCodeList) {
        List<String> list = classroomService.getNameList(classroomCodeList);

        return Result.ok(list);
    }

    @GetMapping("export")
    @Operation(summary = "导出教室")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject ClassroomQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, ClassroomVO.class, classroomService, "page", ClassroomQuery.class, query, "教室", null, null);
    }

}