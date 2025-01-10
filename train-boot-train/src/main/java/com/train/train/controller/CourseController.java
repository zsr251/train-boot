package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.query.StudentQuery;
import com.train.train.vo.StudentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.CourseConvert;
import com.train.train.entity.CourseEntity;
import com.train.train.service.CourseService;
import com.train.train.query.CourseQuery;
import com.train.train.vo.CourseVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 课程
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/course")
@Tag(name="课程")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:course:page')")
    public Result<PageResult<CourseVO>> page(@ParameterObject @Valid CourseQuery query){
        PageResult<CourseVO> page = courseService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:course:info')")
    public Result<CourseVO> get(@PathVariable("id") Long id){
        CourseEntity entity = courseService.getById(id);

        return Result.ok(CourseConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:course:save')")
    public Result<String> save(@RequestBody CourseVO vo){
        courseService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:course:update')")
    public Result<String> update(@RequestBody @Valid CourseVO vo){
        courseService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:course:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        courseService.delete(idList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public Result<List<String>> nameList(@RequestBody List<String> courseCodeList) {
        List<String> list = courseService.getNameList(courseCodeList);

        return Result.ok(list);
    }
    @GetMapping("export")
    @Operation(summary = "导出课程")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject CourseQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, CourseVO.class, courseService, "page",CourseQuery.class, query,"课程", null,null);
    }
}