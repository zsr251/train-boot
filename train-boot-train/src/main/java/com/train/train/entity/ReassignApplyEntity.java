package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 调课申请
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_reassign_apply")
public class ReassignApplyEntity extends BaseEntity {

	/**
	* 申请编码
	*/
	private String applyCode;

	/**
	* 调课类型
	*/
	private String reassignType;

	/**
	* 学员ID
	*/
	private Long studentId;

	/**
	* 老师编码
	*/
	private String teacherCode;

	/**
	* 课程ID
	*/
	private String lessonId;

	/**
	* 申请原因
	*/
	private String applyReason;

	/**
	* 审批状态
	*/
	private String approvalStatus;

	/**
	* 变更为课程ID
	*/
	private String toLessonId;







	/**
	* 租户ID
	*/
	private Long tenantId;

}