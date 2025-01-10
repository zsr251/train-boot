package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.common.excel.LocalDateTimeConverter;
import com.train.framework.common.utils.DateUtils;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.TransTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
* 消课记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "消课记录")
public class CourseHourDepleteLogVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelProperty("消课记录ID")
	@Schema(description = "id")
	private Long id;

	@ExcelProperty("学员ID")
	@TransTable(table = "tt_student", key = "id", fields = {"student_name"}, refs = {"studentName"})
	@Schema(description = "学员ID")
	private Long studentId;

	@ExcelProperty("学员姓名")
	@Schema(description = "学员姓名")
	private String studentName;

	@ExcelIgnore
	@Schema(description = "课程ID")
	private Long lessonId;

	@ExcelIgnore
	@Schema(description = "班级课程ID")
	private Long classCourseId;

	@ExcelProperty("班级编码")
	@TransTable(table = "tt_class_info", key = "class_code", fields = {"class_name"}, refs = {"className"})
	@Schema(description = "班级编码")
	private String classCode;

	@ExcelProperty("班级名称")
	@Schema(description = "班级名称")
	private String className;

	@ExcelProperty("课程编码")
	@TransTable(table = "tt_course", key = "course_code", fields = {"course_name"}, refs = {"courseName"})
	@Schema(description = "课程编码")
	private String courseCode;

	@ExcelProperty("课程名称")
	@Schema(description = "课程名称")
	private String courseName;

	@ExcelProperty("教室编码")
	@TransTable(table = "tt_classroom", key = "classroom_code", fields = {"classroom_name"}, refs = {"classroomName"})
	@Schema(description = "教室编码")
	private String classroomCode;

	@ExcelProperty("教室名称")
	@Schema(description = "教室名称")
	private String classroomName;

	@ExcelProperty("任课老师")
	@TransTable(table = "tt_teacher", key = "teacher_code", fields = {"teacher_name"}, refs = {"teacherName"})
	@Schema(description = "任课老师")
	private String teacherCode;

	@ExcelProperty("任课老师姓名")
	@Schema(description = "任课老师姓名")
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

	@ExcelProperty(value = "到课状态",converter = DictConverter.class)
	@Dict(key = "lesson_arrival_status")
	@Schema(description = "到课状态")
	private String arrivalStatus;

	@ExcelProperty("扣课时数")
	@Schema(description = "扣课时数")
	private Integer courseHour;

	@ExcelProperty(value = "消课时间" ,converter = LocalDateTimeConverter.class)
	@Schema(description = "消课时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime depleteTime;

	@ExcelProperty("消课前课时")
	@Schema(description = "消课前课时")
	private Integer courseHourBefore;

	@ExcelProperty("消课后课时")
	@Schema(description = "消课后课时")
	private Integer courseHourAfter;

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