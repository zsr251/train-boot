package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import com.train.framework.dict.annotations.TransTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
* 班级
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@DictNeed
@Data
@Accessors(chain = true)
@Schema(description = "班级")
public class ClassInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelIgnore
	@Schema(description = "id")
	private Long id;
	@ExcelProperty("班级编码")
	@Schema(description = "班级编码")
	private String classCode;
	@ExcelProperty("班级名称")
	@Schema(description = "班级名称")
	private String className;

	@ExcelProperty(value = "班级类型",converter = DictConverter.class)
	@Dict(key = "class_type")
	@Schema(description = "班级类型一对一or一对多")
	private String classType;

	@ExcelProperty("班级容量")
	@Schema(description = "班级容量")
	private Integer capacity;

	@ExcelProperty("关联课程编码")
	@TransTable(table = "tt_course", key = "course_code", fields = {"course_name"}, refs = {"courseName"})
	@Schema(description = "关联课程")
	private String courseCode;

	@ExcelProperty("关联课程")
	@Schema(description = "关联课程")
	private String courseName;

	@ExcelProperty("管理老师")
	@TransTable(table = "tt_teacher", key = "teacher_code", fields = {"teacher_name"}, refs = {"managerTeacherName"})
	@Schema(description = "管理老师")
	private String managerTeacher;

	@ExcelProperty("管理老师")
	@Schema(description = "管理老师")
	private String managerTeacherName;

	@ExcelProperty(value = "班级状态",converter = DictConverter.class)
	@Dict(key = "class_status")
	@Schema(description = "班级状态")
	private String classStatus;

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
	@ExcelIgnore
	@Schema(description = "班级学员")
	private List<CourseEnrollVO> classStudents;

}