package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.common.utils.JsonUtils;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.query.ClassInfoQuery;
import com.train.train.vo.ClassInfoVO;
import com.train.train.vo.WageRuleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.convert.TeacherConvert;
import com.train.train.entity.TeacherEntity;
import com.train.train.service.TeacherService;
import com.train.train.query.TeacherQuery;
import com.train.train.vo.TeacherVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
* 教师
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/teacher")
@Tag(name="教师")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:teacher:page')")
    public Result<PageResult<TeacherVO>> page(@ParameterObject @Valid TeacherQuery query){
        PageResult<TeacherVO> page = teacherService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:teacher:info')")
    public Result<TeacherVO> get(@PathVariable("id") Long id){
        TeacherEntity entity = teacherService.getById(id);

        return Result.ok(TeacherConvert.INSTANCE.convert(entity));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:teacher:save')")
    public Result<String> save(@RequestBody TeacherVO vo){
        teacherService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:teacher:update')")
    public Result<String> update(@RequestBody @Valid TeacherVO vo){
        teacherService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:teacher:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        teacherService.delete(idList);

        return Result.ok();
    }

    @PostMapping("nameList")
    @Operation(summary = "名称列表")
    public Result<List<String>> nameList(@RequestBody List<String> teacherCodeList) {
        List<String> list = teacherService.getNameList(teacherCodeList);

        return Result.ok(list);
    }

    // 获取老师工资方案
    @GetMapping("wageRule/{id}")
    @Operation(summary = "获取老师工资方案")
    public Result<WageRuleVO> getWageRule(@PathVariable("id") Long id) {
        TeacherEntity entity = teacherService.getById(id);
        WageRuleVO vo = JsonUtils.parseObject(entity.getWageRule(), WageRuleVO.class);
        if (vo == null){
            vo = new WageRuleVO();
        }
        return Result.ok(vo);
    }
    // 更新老师工资方案
    @PostMapping("updateWageRule/{id}")
    @Operation(summary = "更新老师工资方案")
    public Result<String> updateWageRule(@PathVariable("id") Long id,@RequestBody WageRuleVO vo) {
        TeacherEntity entity = new TeacherEntity();
        entity.setId(id);
        entity.setWageRule(JsonUtils.toJsonString(vo));
        teacherService.updateById(entity);
        return Result.ok();
    }

    @GetMapping("export")
    @Operation(summary = "导出老师")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject TeacherQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, TeacherVO.class, teacherService, "page", TeacherQuery.class, query, "老师", null, null);
    }

    public static void main(String[] args) {
        WageRuleVO vo = new WageRuleVO().setFixedSalary(new BigDecimal(100));
        List<WageRuleVO.FixedVO> fixedList = new ArrayList<>();
        fixedList.add(new WageRuleVO.FixedVO().setCode("a").setName("固定工资").setValue(new BigDecimal(100)));
        fixedList.add(new WageRuleVO.FixedVO().setCode("b").setName("其他固定").setValue(new BigDecimal(100)));
        vo.setFixedList(fixedList);
        List<WageRuleVO.CourseVO> courseList = new ArrayList<>();
        courseList.add(new WageRuleVO.CourseVO().setCourseCode("a").setCourseName("绩效工资").setOneHourAmount(new BigDecimal(100)).setDesc("原来01块"));
        courseList.add(new WageRuleVO.CourseVO().setCourseCode("a").setCourseName("绩效工资").setOneHourAmount(new BigDecimal(100)).setDesc("原来01块"));
        vo.setCourseList(courseList);
        String s = JsonUtils.toJsonString(vo);
        System.out.println(s);
        WageRuleVO vv = JsonUtils.parseObject(s, WageRuleVO.class);
        System.out.println(vv);
    }
}