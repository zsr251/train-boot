package com.train.train.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.datapermission.annotations.DataPermission;
import com.train.framework.datapermission.utils.DataPermissionUtils;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.query.LessonTimetableQuery;
import com.train.train.vo.LessonTimetableVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.StudentConvert;
import com.train.train.entity.StudentEntity;
import com.train.train.service.StudentService;
import com.train.train.query.StudentQuery;
import com.train.train.vo.StudentVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/student")
@Tag(name="学员")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @DataPermission(components = {"train/student/index"})
    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:student:page')")
    public Result<PageResult<StudentVO>> page(@ParameterObject @Valid StudentQuery query){
        PageResult<StudentVO> page = studentService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:student:info')")
    public Result<StudentVO> get(@PathVariable("id") Long id){
        StudentEntity entity = studentService.getById(id);

        return Result.ok(StudentConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:student:save')")
    public Result<String> save(@RequestBody StudentVO vo){
        studentService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:student:update')")
    public Result<String> update(@RequestBody @Valid StudentVO vo){
        studentService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:student:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        studentService.delete(idList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public Result<List<String>> nameList(@RequestBody List<Long> idList) {
        List<String> list = studentService.getNameList(idList);

        return Result.ok(list);
    }
    @GetMapping("getUrl/{id}")
    @Operation(summary = "获取学员登录URL")
    public Result<String> getUrl(@PathVariable("id") Long id) {
        String url = studentService.getUrl(id);

        return Result.ok(url);
    }
    @PostMapping("refreshUrl/{id}")
    @Operation(summary = "刷新学员登录URL")
    public Result<String> refreshUrl(@PathVariable("id") Long id) {
        String url = studentService.refreshUrl(id);

        return Result.ok(url);
    }

    @GetMapping("export")
    @Operation(summary = "导出学员")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject StudentQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, StudentVO.class, studentService, "page",StudentQuery.class, query,"学员", null,null);
    }
}