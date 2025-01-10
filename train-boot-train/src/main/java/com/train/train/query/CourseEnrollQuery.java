package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 课程报名查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "课程报名查询")
public class CourseEnrollQuery extends Query {
    @Schema(description = "学员ID")
    private Long studentId;
    @Schema(description = "课程编码")
    private String courseCode;
    @Schema(description = "老师编码")
    private String teacherCode;
    @Schema(description = "报名状态")
    private String enrollStatus;
}