package com.train.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.ClassStudentConvert;
import com.train.train.entity.ClassStudentEntity;
import com.train.train.service.ClassStudentService;
import com.train.train.query.ClassStudentQuery;
import com.train.train.vo.ClassStudentVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 班级学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/class_student")
@Tag(name="班级学员")
@AllArgsConstructor
public class ClassStudentController {
    private final ClassStudentService classStudentService;

    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<ClassStudentVO>> page(@ParameterObject @Valid ClassStudentQuery query){
        PageResult<ClassStudentVO> page = classStudentService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<ClassStudentVO> get(@PathVariable("id") Long id){
        ClassStudentEntity entity = classStudentService.getById(id);

        return Result.ok(ClassStudentConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody ClassStudentVO vo){
        classStudentService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody @Valid ClassStudentVO vo){
        classStudentService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    public Result<String> delete(@RequestBody List<Long> idList){
        classStudentService.delete(idList);

        return Result.ok();
    }
}