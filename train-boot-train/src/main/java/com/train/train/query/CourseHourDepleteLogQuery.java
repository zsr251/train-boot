package com.train.train.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
* 消课记录查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "消课记录查询")
public class CourseHourDepleteLogQuery extends Query {

    @Schema(description = "学员ID")
    private Long studentId;
    @Schema(description = "课程编码")
    private String courseCode;
    @Schema(description = "老师编码")
    private String teacherCode;

    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @Schema(description = "课消时间-开始")
    private LocalDateTime depleteTimeBegin;
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @Schema(description = "课消时间-结束")
    private LocalDateTime depleteTimeEnd;
}