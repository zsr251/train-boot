package com.train.train.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.train.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_order")
public class OrderEntity  extends BaseEntity {
    /**
     * 学员ID
     */
    private Long studentId;
    /**
     * 需要支付的金额
     */
    private BigDecimal shouldPay;
    /**
     * 已支付金额
     */
    private BigDecimal paid;
    /**
     * 手续费
     */
    private BigDecimal serviceFee;
    /**
     * 实际收到金额
     */
    private BigDecimal actuallyReceived;
    /**
     * 支付时间
     */
    private LocalDateTime paidTime;
    /**
     * 订单总金额
     */
    private BigDecimal totalPrice;
    /**
     * 减免金额
     */
    private BigDecimal discount;
    /**
     * 课程明细
     */
    private String courseDetail;
    /**
     * 减免原因
     */
    private String discountReason;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 备注
     */
    private String remark;
    /**
     * 租户ID
     */
    private Long tenantId;
}
