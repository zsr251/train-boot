package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* 课时购买记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "课时购买记录")
public class CourseHourBuyLogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "订单ID")
	private Long orderId;

	@Schema(description = "学员ID")
	private Long studentId;

	@Schema(description = "课程编码")
	private String courseCode;

	@Schema(description = "课时单价")
	private BigDecimal amountOneHour;

	@Schema(description = "购买课时")
	private Integer courseHourBuy;

	@Schema(description = "订单金额")
	private BigDecimal orderAmount;

	@Schema(description = "购买前课时")
	private Integer courseHourBefore;

	@Schema(description = "购买后课时")
	private Integer courseHourAfter;

	@Schema(description = "备注")
	private String remark;

	@Schema(description = "版本号")
	private Integer version;

	@Schema(description = "删除标识  0：正常   1：已删除")
	private Integer deleted;

	@Schema(description = "创建者")
	private Long creator;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新者")
	private Long updater;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	@Schema(description = "租户ID")
	private Long tenantId;


}