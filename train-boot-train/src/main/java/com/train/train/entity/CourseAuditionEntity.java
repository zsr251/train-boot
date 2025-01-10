package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 课程试听
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_course_audition")
public class CourseAuditionEntity extends BaseEntity {

	/**
	* 学员ID
	*/
	private Long studentId;

	/**
	* 课程计划ID
	*/
	private Long lessonId;

	/**
	* 课程编码
	*/
	private String courseCode;

	/**
	* 班级编码
	*/
	private String classCode;

	/**
	* 教室编码
	*/
	private String classroomCode;

	/**
	* 老师编码
	*/
	private String teacherCode;

	/**
	* 试听状态
	*/
	private String auditionStatus;

	/**
	* 试听反馈
	*/
	private String auditionFeedback;

	/**
	* 审批状态
	*/
	private String approvalStatus;







	/**
	* 租户ID
	*/
	private Long tenantId;

}