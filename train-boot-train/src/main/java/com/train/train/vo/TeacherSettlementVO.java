package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.common.excel.LocalDateTimeConverter;
import com.train.framework.common.utils.DateUtils;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.TransTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 员工工资条
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Data
@Accessors(chain = true)
@Schema(description = "员工工资条")
public class TeacherSettlementVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    @Schema(description = "id")
    private Long id;

    @ExcelProperty("结算名称")
    @Schema(description = "结算名称")
    private String settlementName;

    @ExcelProperty("结算金额")
    @Schema(description = "结算金额")
    private BigDecimal settlementAmount;

    @ExcelProperty(value="结算日期", converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = DateUtils.DATE_PATTERN)
    @Schema(description = "结算日期")
    private LocalDateTime settlementDate;

    @ExcelProperty("结算对象")
    @TransTable(table = "tt_teacher", key = "teacher_code", fields = "teacher_name", refs = "teacherName")
    @Schema(description = "结算对象")
    private String teacherCode;

    @ExcelProperty("结算对象")
    @Schema(description = "结算对象")
    private String teacherName;

    @ExcelProperty(value="结算开始日期", converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = DateUtils.DATE_PATTERN)
    @Schema(description = "结算开始日期包含")
    private LocalDateTime settlementBeginDate;

    @ExcelProperty(value="结算结束日期", converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = DateUtils.DATE_PATTERN)
    @Schema(description = "结算结束日期包含")
    private LocalDateTime settlementEndDate;

    @ExcelProperty(value="发薪日", converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = DateUtils.DATE_PATTERN)
    @Schema(description = "发薪日")
    private LocalDateTime payDate;

    @ExcelProperty("经办人")
    @Schema(description = "经办人")
    private String processor;

    @ExcelIgnore
    @Schema(description = "结算详情")
    private String settlementDetail;

    @ExcelProperty(value = "结算状态", converter = DictConverter.class)
    @Dict(key = "settlement_status")
    @Schema(description = "结算状态")
    private String settlementStatus;

    @ExcelProperty("备注")
    @Schema(description = "备注")
    private String remark;
    @ExcelIgnore
    @Schema(description = "版本号")
    private Integer version;
    @ExcelIgnore
    @Schema(description = "删除标识  0：正常   1：已删除")
    private Integer deleted;
    @ExcelIgnore
    @Schema(description = "创建者")
    private Long creator;
    @ExcelIgnore
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @ExcelIgnore
    @Schema(description = "更新者")
    private Long updater;
    @ExcelIgnore
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @ExcelIgnore
    @Schema(description = "租户ID")
    private Long tenantId;


}