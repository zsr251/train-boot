package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
* 请假申请
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "请假申请")
public class LeaveApplyVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "申请编码")
	private String applyCode;

	@Schema(description = "请假类型")
	private String leaveType;

	@Schema(description = "学员ID")
	private Long studentId;

	@Schema(description = "老师编码")
	private String teacherCode;

	@Schema(description = "课程ID")
	private String lessonId;

	@Schema(description = "申请原因")
	private String applyReason;

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