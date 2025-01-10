package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 员工工资条
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_teacher_settlement")
public class TeacherSettlementEntity extends BaseEntity {

	/**
	* 结算名称
	*/
	private String settlementName;

	/**
	* 结算金额
	*/
	private BigDecimal settlementAmount;

	/**
	* 结算日期
	*/
	private LocalDateTime settlementDate;

	/**
	* 结算对象
	*/
	private String teacherCode;

	/**
	* 结算开始日期包含
	*/
	private LocalDateTime settlementBeginDate;

	/**
	* 结算结束日期包含
	*/
	private LocalDateTime settlementEndDate;

	/**
	* 发薪日
	*/
	private LocalDateTime payDate;

	/**
	* 经办人
	*/
	private String processor;

	/**
	* 结算详情
	*/
	private String settlementDetail;

	/**
	* 结算状态
	*/
	private String settlementStatus;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}