package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;

import lombok.experimental.Accessors;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 学员课程课时
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@Accessors(chain = true)
@TableName("tt_student_course_hour")
public class StudentCourseHourEntity extends BaseEntity {

	/**
	* 学员ID
	*/
	private Long studentId;

	/**
	* 课程编码
	*/
	private String courseCode;

	/**
	* 总消耗课时
	*/
	private Integer courseHourDeplete;

	/**
	* 剩余课时
	*/
	private Integer courseHourLeft;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}