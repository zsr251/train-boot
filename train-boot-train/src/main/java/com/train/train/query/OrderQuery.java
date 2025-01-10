package com.train.train.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.query.Query;
import com.train.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "订单查询")
public class OrderQuery  extends Query {
    @Schema(description = "学员ID")
    private Long studentId;
    @Schema(description = "订单状态")
    private String orderStatus;
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @Schema(description = "支付时间-开始")
    private LocalDateTime paidTimeBegin;
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @Schema(description = "支付时间-结束")
    private LocalDateTime paidTimeEnd;
}