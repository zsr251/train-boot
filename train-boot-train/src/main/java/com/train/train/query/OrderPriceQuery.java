package com.train.train.query;

import com.train.train.vo.CoursePriceVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "课程价格查询")
public class OrderPriceQuery {
    @Schema(description = "学员ID")
    private Long studentId;
    @Schema(description = "课程及课时")
    private List<CoursePriceVO> courseList;
    @Schema(description = "总金额")
    private BigDecimal totalPrice;
}
