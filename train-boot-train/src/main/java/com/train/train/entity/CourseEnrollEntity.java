package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 课程报名
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_course_enroll")
public class CourseEnrollEntity extends BaseEntity {

	/**
	* 学员ID
	*/
	private Long studentId;

	/**
	* 课程编码
	*/
	private String courseCode;

	/**
	* 老师编码
	*/
	private String teacherCode;

	/**
	* 报名状态
	*/
	private String enrollStatus;







	/**
	* 租户ID
	*/
	private Long tenantId;

}