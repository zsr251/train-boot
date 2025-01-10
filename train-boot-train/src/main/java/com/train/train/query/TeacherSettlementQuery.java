package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

import java.time.LocalDateTime;

/**
* 员工工资条查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "员工工资条查询")
public class TeacherSettlementQuery extends Query {
    @Schema(description = "结算名称")
    private String settlementName;

    @Schema(description = "结算日期")
    private LocalDateTime settlementDate;

    @Schema(description = "结算对象")
    private String teacherCode;

    @Schema(description = "发薪日")
    private LocalDateTime payDate;

    @Schema(description = "经办人")
    private String processor;

}