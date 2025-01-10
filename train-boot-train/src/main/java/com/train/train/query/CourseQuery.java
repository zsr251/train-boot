package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 课程查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "课程查询")
public class CourseQuery extends Query {
    @Schema(description = "课程编码")
    private String courseCode;
    @Schema(description = "任课老师")
    private String teacherCode;
    @Schema(description = "课程名称")
    private String courseName;
    @Schema(description = "课程类型")
    private String courseType;
    @Schema(description = "关联的班级人数类型")
    private String classType;
    @Schema(description = "课程状态")
    private String courseStatus;
}