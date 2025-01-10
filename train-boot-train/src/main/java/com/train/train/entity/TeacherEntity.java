package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 教师
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_teacher")
public class TeacherEntity extends BaseEntity {

	/**
	* 登录名
	*/
	private String teacherCode;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	* 老师名称
	*/
	private String teacherName;

	/**
	* 手机号
	*/
	private String phone;

	/**
	* 工作类型 兼职全职
	*/
	private String jobType;

	/**
	* 资格证
	*/
	private String certification;

	/**
	* 工资方案
	*/
	private String wageRule;

	/**
	* 备注
	*/
	private String remark;

	/**
	* 状态 离职 在职
	*/
	private String status;







	/**
	* 租户ID
	*/
	private Long tenantId;

}