package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
 * 学员查询
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "学员查询")
public class StudentQuery extends Query {
    @Schema(description = "学员姓名")
    private String studentName;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "学员类型")
    private String studentType;
    @Schema(description = "学员来源类型")
    private String sourceType;
    @Schema(description = "状态normal/block")
    private String status;
    @Schema(description = "学员身份证号")
    private String idCard;
    @Schema(description = "意向级别或程度")
    private String intentionLevel;
    @Schema(description = "跟进状态")
    private String followupStatus;
    @Schema(description = "跟进人")
    private String followupPerson;
    @Schema(description = "学管师姓名")
    private String academicAdvisor;
}