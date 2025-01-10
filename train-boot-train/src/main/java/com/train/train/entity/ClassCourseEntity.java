package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 班级课程
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_class_course")
public class ClassCourseEntity extends BaseEntity {

	/**
	* 班级编码
	*/
	private String classCode;

	/**
	* 课程编码
	*/
	private String courseCode;

	/**
	 * 老师编码
	 */
	private String teacherCode;

	/**
	* 教室编码
	*/
	private String classroomCode;

	/**
	* 计划课时数
	*/
	private Integer planHours;

	/**
	 * 单次上课课时
	 */
	private Integer courseHourOnce;

	/**
	* 已授课时
	*/
	private Integer completedHours;

	/**
	* 已上课次数
	*/
	private Integer completedTimes;

	/**
	 * 已排课时数
	 */
	private Integer scheduleHours;

	/**
	* 排课规则
	*/
	private String scheduleRule;

	/**
	* 排课状态
	*/
	private String scheduleStatus;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}