package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.datapermission.annotations.DataPermission;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.vo.LessonNamedVO;
import com.train.train.vo.LessonTimetableAppointShowVO;
import com.train.train.vo.LessonTimetableGroupShowVO;
import com.train.train.vo.LessonTimetableVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.train.query.TimetableQuery;
import com.train.train.service.LessonTimetableService;
import com.train.train.query.LessonTimetableQuery;
import com.train.train.vo.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 课程计划
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@RestController
@RequestMapping("train/lesson_timetable")
@Tag(name="课程计划")
@AllArgsConstructor
public class LessonTimetableController {
    private final LessonTimetableService lessonTimetableService;

    @DataPermission(components = {"train/lesson/index"})
    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:lesson_timetable:page')")
    public Result<PageResult<LessonTimetableVO>> page(@ParameterObject @Valid LessonTimetableQuery query){
        PageResult<LessonTimetableVO> page = lessonTimetableService.page(query);

        return Result.ok(page);
    }

    @GetMapping("today")
    @Operation(summary = "今天课程")
    public Result<List<LessonTimetableVO>> today(@ParameterObject LessonTimetableQuery query){
        List<LessonTimetableVO> voList = lessonTimetableService.today(query);

        return Result.ok(voList);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    public Result<LessonTimetableVO> get(@PathVariable("id") Long id){

        return Result.ok(lessonTimetableService.getVOById(id));
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:lesson_timetable:save')")
    public Result<String> save(@RequestBody LessonTimetableVO vo){
        lessonTimetableService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:lesson_timetable:update')")
    public Result<String> update(@RequestBody @Valid LessonTimetableVO vo){
        lessonTimetableService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:lesson_timetable:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        lessonTimetableService.delete(idList);

        return Result.ok();
    }

    // 多老师课表
    @GetMapping("teachers")
    @Operation(summary = "多老师课表")
    public Result<LessonTimetableGroupShowVO> getTeachersTimetable(@RequestParam Integer week){

        return Result.ok(lessonTimetableService.getTeachersTimetable(week));
    }
    // 多教室课表
    @GetMapping("classrooms")
    @Operation(summary = "多教室课表")
    public Result<LessonTimetableGroupShowVO> getClassroomsTimetable(@RequestParam Integer week){

        return Result.ok(lessonTimetableService.getClassroomsTimetable(week));
    }
    @GetMapping("appoint")
    @Operation(summary = "单学生、班级、老师、教室课表")
    public Result<LessonTimetableAppointShowVO> getAppointTimetable(@ParameterObject TimetableQuery query){

        return Result.ok(lessonTimetableService.getAppointTimetable(query));
    }

    @RepeatRequestLimit(expireSeconds = 30)
    @PostMapping("named")
    @Operation(summary = "点名")
    public Result<String> named(@RequestBody LessonNamedVO vo){
        lessonTimetableService.lessonNamed(vo);

        return Result.ok();
    }

    @RepeatRequestLimit(expireSeconds = 30)
    @PostMapping("reassign")
    @Operation(summary = "调课")
    public Result<String> lessonReassign(@RequestBody LessonTimetableVO vo){
        lessonTimetableService.lessonReassign(vo);

        return Result.ok();
    }

    @RepeatRequestLimit(expireSeconds = 30)
    @PostMapping("cancel")
    @Operation(summary = "撤销")
    public Result<String> lessonCancel(@RequestBody LessonTimetableVO vo){
        lessonTimetableService.lessonCancel(vo.getId());

        return Result.ok();
    }


    @GetMapping("export")
    @Operation(summary = "导出课程计划")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject LessonTimetableQuery query,HttpServletResponse response) {
        ExcelUtils.writeExcel(response, LessonTimetableVO.class, lessonTimetableService, "page",LessonTimetableQuery.class, query,"课程计划", null,null);
    }
}