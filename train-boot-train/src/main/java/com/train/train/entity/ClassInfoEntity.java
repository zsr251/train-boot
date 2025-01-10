package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 班级
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_class_info")
public class ClassInfoEntity extends BaseEntity {

	/**
	* 班级编码
	*/
	private String classCode;

	/**
	* 班级名称
	*/
	private String className;

	/**
	* 班级类型一对一or一对多
	*/
	private String classType;

	/**
	* 班级容量
	*/
	private Integer capacity;

	/**
	* 关联课程
	*/
	private String courseCode;

	/**
	* 管理老师
	*/
	private String managerTeacher;

	/**
	* 班级状态
	*/
	private String classStatus;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}