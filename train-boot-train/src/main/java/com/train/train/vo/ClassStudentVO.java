package com.train.train.vo;

import com.train.framework.dict.annotations.TransTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 班级学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "班级学员")
public class ClassStudentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "班级编码")
	private String classCode;
	@TransTable(table = "tt_student", key = "id", fields = {"student_name","phone"}, refs = {"studentName","phone"})
	@Schema(description = "学员ID")
	private Long studentId;

	@Schema(description = "学员名称")
	private String studentName;

	@Schema(description = "学员手机号")
	private String phone;


	@Schema(description = "入班时间")
	private LocalDateTime enterTime;

	@Schema(description = "状态")
	private String status;

	@Schema(description = "备注")
	private String remark;

	@Schema(description = "版本号")
	private Integer version;

	@Schema(description = "删除标识  0：正常   1：已删除")
	private Integer deleted;

	@Schema(description = "创建者")
	private Long creator;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新者")
	private Long updater;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	@Schema(description = "租户ID")
	private Long tenantId;

	@Schema(description = "关联报名ID")
	private Long courseEnrollId;

}