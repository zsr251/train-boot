package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
* 课程试听
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "课程试听")
public class CourseAuditionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "学员ID")
	private Long studentId;

	@Schema(description = "课程计划ID")
	private Long lessonId;

	@Schema(description = "课程编码")
	private String courseCode;

	@Schema(description = "班级编码")
	private String classCode;

	@Schema(description = "教室编码")
	private String classroomCode;

	@Schema(description = "老师编码")
	private String teacherCode;

	@Schema(description = "试听状态")
	private String auditionStatus;

	@Schema(description = "试听反馈")
	private String auditionFeedback;

	@Schema(description = "审批状态")
	private String approvalStatus;

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


}