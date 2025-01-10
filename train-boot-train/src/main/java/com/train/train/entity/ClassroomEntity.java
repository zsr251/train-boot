package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 教室
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_classroom")
public class ClassroomEntity extends BaseEntity {

	/**
	* 教室编码
	*/
	private String classroomCode;

	/**
	* 教室名称
	*/
	private String classroomName;

	/**
	* 教室容量
	*/
	private Integer capacity;

	/**
	* 教室地址
	*/
	private String address;

	/**
	* 教室状态
	*/
	private String classroomStatus;

	/**
	* 备注
	*/
	private String remark;

	/**
	 * 忽略排课冲突
	 */
	private Integer ignoreConflict;

	/**
	* 租户ID
	*/
	private Long tenantId;

}