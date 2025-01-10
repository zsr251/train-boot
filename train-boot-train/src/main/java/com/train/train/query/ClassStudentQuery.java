package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 班级学员查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "班级学员查询")
public class ClassStudentQuery extends Query {
    @Schema(description = "班级编码")
    private String classCode;
    @Schema(description = "学员ID")
    private Long studentId;
    @Schema(description = "状态")
    private String status;

}