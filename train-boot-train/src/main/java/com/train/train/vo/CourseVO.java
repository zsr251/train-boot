package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import com.train.framework.dict.annotations.TransTable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* 课程
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@DictNeed
@Data
@Accessors(chain = true)
@Schema(description = "课程")
public class CourseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	@Schema(description = "id")
	private Long id;

	@ExcelProperty("课程编码")
	@NotBlank(message = "课程编码不能为空")
	@Schema(description = "课程编码")
	private String courseCode;

	@ExcelProperty("课程名称")
	@NotBlank(message = "课程名称不能为空")
	@Schema(description = "课程名称")
	private String courseName;

	@ExcelProperty("任课老师")
	@TransTable(table = "tt_teacher", key = "teacher_code", fields = {"teacher_name"}, refs = {"teacherName"})
	@Schema(description = "任课老师")
	private String teacherCode;

	@ExcelProperty("任课老师")
	@Schema(description = "任课老师")
	private String teacherName;

	@ExcelProperty("总课时数")
	@Schema(description = "总课时数")
	private Integer courseHourTotal;

	@ExcelProperty("每次课课时")
	@Schema(description = "每次课课时")
	private Integer courseHourOnce;

	@ExcelProperty("每课时收费")
	@Schema(description = "每课时收费")
	private BigDecimal amountOneHour;

	@ExcelProperty("课程简介")
	@Schema(description = "课程简介")
	private String courseDesc;

	@ExcelProperty(value = "课程状态",converter = DictConverter.class)
	@Dict(key = "course_status")
	@Schema(description = "课程状态new/normal/close")
	private String courseStatus;

	@ExcelProperty(value = "关联班级类型",converter = DictConverter.class)
	@Dict(key = "class_type")
	@Schema(description = "关联的班级人数类型一对一一对多")
	private String classType;

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