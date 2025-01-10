package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;
import com.train.framework.common.utils.DateUtils;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
* 课程计划查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "课程计划查询")
public class LessonTimetableQuery extends Query {
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
    @Schema(description = "课程状态")
    private String lessonStatus;
    @DateTimeFormat(pattern = DateUtils.DATE_PATTERN)
    @Schema(description = "课程日期")
    private LocalDate lessonDate;
}