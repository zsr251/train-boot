package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;

import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 课程
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_course")
public class CourseEntity extends BaseEntity {

	/**
	* 课程编码
	*/
	private String courseCode;

	/**
	* 课程名称
	*/
	private String courseName;

	/**
	* 任课老师
	*/
	private String teacherCode;

	/**
	* 总课时数
	*/
	private Integer courseHourTotal;

	/**
	* 每次课课时
	*/
	private Integer courseHourOnce;

	/**
	* 每课时收费
	*/
	private BigDecimal amountOneHour;

	/**
	* 课程简介
	*/
	private String courseDesc;

	/**
	* 课程状态new/normal/close
	*/
	private String courseStatus;

	/**
	* 关联的班级人数类型一对一一对多
	*/
	private String classType;

	/**
	* 备注
	*/
	private String remark;

	/**
	* 租户ID
	*/
	private Long tenantId;

}