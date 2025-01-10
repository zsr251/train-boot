package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.common.excel.LocalDateTimeConverter;
import com.train.framework.common.utils.DateUtils;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import com.train.framework.dict.annotations.TransTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@DictNeed
@Data
@Accessors(chain = true)
@Schema(description = "订单")
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelProperty("订单ID")
    @Schema(description = "订单ID")
    private Long id;
    @ExcelIgnore
    @TransTable(table = "tt_student", key = "id", fields = {"student_name","phone"}, refs = {"studentName","studentPhone"})
    @Schema(description = "学员ID")
    private Long studentId;
    @ExcelProperty("学员姓名")
    @Schema(description = "学员姓名")
    private String studentName;
    @ExcelProperty("学员手机号")
    @Schema(description = "学员手机号")
    private String studentPhone;
    @ExcelProperty("已支付金额")
    @Schema(description = "已支付金额")
    private BigDecimal paid;
    @ExcelProperty("实际收到金额")
    @Schema(description = "实际收到金额")
    private BigDecimal actuallyReceived;
    @ExcelProperty("通道手续费")
    @Schema(description = "通道手续费")
    private BigDecimal serviceFee;
    @ExcelProperty(value = "支付时间",converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @Schema(description = "支付时间")
    private LocalDateTime paidTime;
    @ExcelProperty("需要支付金额")
    @Schema(description = "需要支付金额")
    private BigDecimal shouldPay;
    @ExcelProperty("订单总金额")
    @Schema(description = "订单总金额")
    private BigDecimal totalPrice;
    @ExcelProperty("减免金额")
    @Schema(description = "减免金额")
    private BigDecimal discount;
    @ExcelProperty("减免原因")
    @Schema(description = "减免原因")
    private String discountReason;
    @ExcelProperty(value = "订单状态",converter = DictConverter.class)
    @Dict(key = "order_status")
    @Schema(description = "订单状态")
    private String orderStatus;
    @ExcelIgnore
    @Schema(description = "课程明细")
    private List<CoursePriceVO> courseList;
    @ExcelProperty("备注")
    @Schema(description = "备注")
    private String remark;
    @ExcelProperty(value = "创建时间",converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
