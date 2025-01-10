package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.train.framework.common.excel.LocalDateTimeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

import lombok.experimental.Accessors;
import com.train.framework.common.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 课程计划
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Data
@Accessors(chain = true)
@Schema(description = "课程计划")
public class LessonTimetableVO implements Serializable{
    private static final long serialVersionUID = 1L;

    @ExcelProperty("课程计划ID")
    @Schema(description = "课程ID")
    private Long id;

    @ExcelIgnore
    @Schema(description = "班级课程ID")
    private Long classCourseId;

    @ExcelProperty("班级编码")
    @Schema(description = "班级编码")
    private String classCode;

    @ExcelProperty("班级名称")
    @Schema(description = "班级名称")
    private String className;

    @ExcelProperty("课程编码")
    @Schema(description = "课程编码")
    private String courseCode;

    @ExcelProperty("课程名称")
    @Schema(description = "课程名称")
    private String courseName;

    @ExcelProperty("教室编码")
    @Schema(description = "教室编码")
    private String classroomCode;
    @ExcelProperty("教室名称")
    @Schema(description = "教室名称")
    private String classroomName;

    @ExcelProperty("任课老师")
    @Schema(description = "任课老师")
    private String teacherCode;
    @ExcelProperty("任课老师名称")
    @Schema(description = "任课老师名称")
    private String teacherName;

    @ExcelProperty(value = "课程日期" ,converter = LocalDateTimeConverter.class)
    @DateTimeFormat(pattern = DateUtils.DATE_PATTERN)
    @JsonFormat(pattern = DateUtils.DATE_PATTERN)
    @Schema(description = "课程日期")
    private LocalDateTime lessonDate;

    @ExcelProperty(value = "课程开始时间" ,converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = "HH:mm")
    @Schema(description = "课程开始时间")
    private LocalDateTime lessonBeginTime;

    @ExcelProperty(value = "课程结束时间" ,converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = "HH:mm")
    @Schema(description = "课程结束时间")
    private LocalDateTime lessonEndTime;

    @ExcelProperty("扣课时数")
    @Schema(description = "扣课时数")
    private Integer courseHour;
    @ExcelIgnore
    @Schema(description = "课程状态")
    private String lessonStatus;
    @ExcelProperty("课程状态")
    @Schema(description = "课程状态解释")
    private String lessonStatusLabel;
    @ExcelProperty("备注")
    @Schema(description = "备注")
    private String remark;
    @ExcelIgnore
    @Schema(description = "版本号")
    private Integer version;
    @ExcelIgnore
    @Schema(description = "删除标识  0：正常   1：已删除")
    private Integer deleted;
    @ExcelIgnore
    @Schema(description = "创建者")
    private Long creator;
    @ExcelIgnore
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @ExcelIgnore
    @Schema(description = "更新者")
    private Long updater;
    @ExcelIgnore
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @ExcelIgnore
    @Schema(description = "租户ID")
    private Long tenantId;


}