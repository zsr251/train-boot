package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 班级学员
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_class_student")
public class ClassStudentEntity extends BaseEntity {

	/**
	* 班级编码
	*/
	private String classCode;

	/**
	* 学员ID
	*/
	private Long studentId;

	/**
	* 入班时间
	*/
	private LocalDateTime enterTime;

	/**
	* 状态
	*/
	private String status;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}