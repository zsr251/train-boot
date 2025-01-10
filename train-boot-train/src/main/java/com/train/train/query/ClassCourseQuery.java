package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 班级课程查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "班级课程查询")
public class ClassCourseQuery extends Query {
    @Schema(description = "班级编码")
    private String classCode;

    @Schema(description = "课程编码")
    private String courseCode;

    @Schema(description = "排课状态")
    private String scheduleStatus;
}