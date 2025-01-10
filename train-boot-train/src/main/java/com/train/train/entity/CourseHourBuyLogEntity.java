package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;

import com.train.framework.mybatis.entity.BaseEntity;
import lombok.experimental.Accessors;

/**
 * 课时购买记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@Accessors(chain = true)
@TableName("tt_course_hour_buy_log")
public class CourseHourBuyLogEntity extends BaseEntity {

	/**
	* 学员ID
	*/
	private Long studentId;
	/**
	 * 订单ID
	 */
	private Long orderId;

	/**
	* 课程编码
	*/
	private String courseCode;

	/**
	* 课时单价
	*/
	private BigDecimal amountOneHour;

	/**
	* 购买课时
	*/
	private Integer courseHourBuy;

	/**
	* 订单金额
	*/
	private BigDecimal orderAmount;

	/**
	* 购买前课时
	*/
	private Integer courseHourBefore;

	/**
	* 购买后课时
	*/
	private Integer courseHourAfter;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}