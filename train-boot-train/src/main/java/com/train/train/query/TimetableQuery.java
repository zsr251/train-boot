package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "课表查询")
public class TimetableQuery {
    @Schema(description = "周偏移 0 当前周 1 下一周 -1 上一周")
    private Integer week = 0;
    @Schema(description = "指定学生")
    private Long studentId;
    @Schema(description = "指定班级")
    private String classCode;
    @Schema(description = "指定老师")
    private String teacherCode;
    @Schema(description = "指定教室")
    private String classroomCode;
    @Schema(description = "指定课程")
    private String courseCode;

}
