package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* 课程价格
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "课程价格")
public class CoursePriceVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "课程编码")
	private String courseCode;

	@Schema(description = "课程名称")
	private String courseName;

	@Schema(description = "总课时数")
	private Integer courseHourTotal;

	@Schema(description = "每次课课时")
	private Integer courseHourOnce;

	@Schema(description = "每课时收费")
	private BigDecimal amountOneHour;

	@Schema(description = "购买课时")
	private Integer buyHours;

	@Schema(description = "应付总金额")
	private BigDecimal totalPrice;

	@Schema(description = "减免金额")
	private BigDecimal discount;

}